package com.unicesumar;

import com.unicesumar.entities.Product;
import com.unicesumar.entities.Sale;
import com.unicesumar.entities.User;
import com.unicesumar.paymentMethods.PaymentType;
import com.unicesumar.repository.ProductRepository;
import com.unicesumar.repository.SaleRepository;
import com.unicesumar.repository.UserRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        ProductRepository listaDeProdutos = null;
        UserRepository listaDeUsuarios = null;
        SaleRepository listaDeVendas = null;

        Connection conn = null;

        // Parâmetros de conexão
        String url = "jdbc:sqlite:database.sqlite";

        // Tentativa de conexão
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                // Criar tabela de vendas se não existir
                Statement stmt = conn.createStatement();
                String createSalesTable = "CREATE TABLE IF NOT EXISTS sales (" +
                        "id TEXT PRIMARY KEY, " +
                        "user_id TEXT, " +
                        "products TEXT, " +
                        "total DOUBLE, " +
                        "payment_method TEXT)";
                stmt.execute(createSalesTable);

                listaDeProdutos = new ProductRepository(conn);
                listaDeUsuarios = new UserRepository(conn);
                listaDeVendas = new SaleRepository(conn, listaDeProdutos);
            } else {
                System.out.println("Falha na conexão.");
                System.exit(1);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            System.exit(1);
        }

        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n-------------MENU-------------");
            System.out.println("1 - Cadastrar Produto");
            System.out.println("2 - Listar Produtos");
            System.out.println("3 - Cadastrar Usuário");
            System.out.println("4 - Listar Usuários");
            System.out.println("5 - Realizar Venda");
            System.out.println("6 - Sair");
            System.out.println("Escolha uma opção: ");
            option = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (option) {
                case 1:
                    System.out.println("===================================================");
                    System.out.println("Cadastrar Produto");
                    System.out.println("Digite o nome: ");
                    String productName = scanner.nextLine();
                    System.out.println("Digite o preço: ");
                    double valorProduto;
                    valorProduto = scanner.nextDouble();
                    scanner.nextLine(); // Consumir a nova linha após o preço
                    listaDeProdutos.save(new Product(productName, valorProduto));
                    System.out.println("Produto cadastrado :)");
                    System.out.println("===================================================");
                    break;
                case 2:
                    System.out.println("===================================================");
                    System.out.println("Listar Produtos");
                    List<Product> products = listaDeProdutos.findAll();
                    if (products.isEmpty()) {
                        System.out.println("Nenhum produto encontrado :(");
                    } else {
                        products.forEach(System.out::println);
                    }
                    System.out.println("===================================================");
                    break;
                case 3:
                    System.out.println("===================================================");
                    System.out.println("Cadastrar Usuário");
                    System.out.println("Digite o nome do usuário: ");
                    String username = scanner.nextLine();
                    System.out.println("Digite o e-mail do usuário: ");
                    String emailUsuario = scanner.nextLine();
                    System.out.println("Digite a senha do usuário: ");
                    String senhaUsuario = scanner.nextLine();
                    listaDeUsuarios.save(new User(username, emailUsuario, senhaUsuario));
                    System.out.println("Usuário cadastrado :)");
                    System.out.println("===================================================");
                    break;
                case 4:
                    System.out.println("===================================================");
                    System.out.println("Listar Usuários");
                    List<User> users = listaDeUsuarios.findAll();
                    if (users.isEmpty()) {
                        System.out.println("Nenhum usuário encontrado :(");
                    } else {
                        users.forEach(System.out::println);
                    }
                    System.out.println("===================================================");
                    break;
                case 5:
                    System.out.println("===================================================");
                    System.out.println("Realizar Venda");

                    // Buscar usuário por e-mail
                    System.out.println("Insira o Email: ");
                    String email = scanner.nextLine();
                    Optional<User> userOpt = listaDeUsuarios.findAll().stream()
                            .filter(user -> user.getEmail().equals(email))
                            .findFirst();
                    if (!userOpt.isPresent()) {
                        System.out.println("Usuário não encontrado :(");
                        break;
                    }
                    User user = userOpt.get();

                    // Buscar produtos por IDs
                    System.out.println("Insira o ID do produto (caso seja mais de um produto separar os IDs por ,): ");
                    String[] idProduto = scanner.nextLine().split(",");
                    List<Product> produtoCarrinho = new ArrayList<>();
                    for (String id : idProduto) {
                        try {
                            UUID productId = UUID.fromString(id.trim());
                            Optional<Product> acharProduto = listaDeProdutos.findById(productId);
                            if (acharProduto.isPresent()) {
                                produtoCarrinho.add(acharProduto.get());
                            } else {
                                System.out.println("Produto não encontrado :(");
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("ID inválido: " + id);
                        }
                    }
                    if (produtoCarrinho.isEmpty()) {
                        System.out.println("Nenhum produto encontrado :(");
                        break;
                    }

                    System.out.println("Produtos selecionado:");
                    produtoCarrinho.forEach(p -> System.out.println("- " + p.getName() + " (R$ " + p.getPrice() + ")"));

                    // Calcular valor total
                    double total = produtoCarrinho.stream().mapToDouble(Product::getPrice).sum();

                    // Escolher forma de pagamento
                    System.out.println("Escolha a forma de pagamento:");
                    System.out.println("1 - Cartão de Crédito");
                    System.out.println("2 - Boleto");
                    System.out.println("3 - PIX");
                    System.out.println("Opção: ");
                    int paymentOption = scanner.nextInt();
                    scanner.nextLine(); // Consumir a nova linha

                    PaymentType paymentType;
                    switch (paymentOption) {
                        case 1:
                            paymentType = PaymentType.CARTAO;
                            break;
                        case 2:
                            paymentType = PaymentType.BOLETO;
                            break;
                        case 3:
                            paymentType = PaymentType.PIX;
                            break;
                        default:
                            System.out.println("Forma de pagamento inválida! Usando PIX como padrão.");
                            paymentType = PaymentType.PIX;
                    }

                    // Processar pagamento usando Strategy e Factory
                    PaymentManager paymentManager = new PaymentManager();
                    paymentManager.setPaymentMethod(PaymentMethodFactory.create(paymentType));
                    System.out.println("===================================================");
                    System.out.println("Efetuando pagamento.......");
                    paymentManager.pay(total);
                    System.out.println("Pagamento confirmado :) ");
                    // Registrar a venda no banco usando SaleRepository
                    Sale sale = new Sale(user.getUuid(), produtoCarrinho, total, paymentType);
                    listaDeVendas.save(sale);

                    // Exibir resumo da venda
                    System.out.println("\nResumo da venda:");
                    System.out.println("Cliente: " + user.getName());
                    System.out.println("Produtos:");
                    produtoCarrinho.forEach(p -> System.out.println("-> " + p.getName()));
                    System.out.println("Valor total: R$ " + total);
                    System.out.println("Método de pagamento: " + paymentType);
                    System.out.println("===================================================");
                    break;

                case 6:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente");
            }

        } while (option != 6);

        scanner.close();
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}