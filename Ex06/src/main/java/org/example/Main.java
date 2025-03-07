package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PaymentProcessor processor = new PaymentProcessor();

        while (true) {
            System.out.println("1: Pix");
            System.out.println("2: Cartão de Crédito");
            System.out.println("3: Boleto");
            System.out.println("0: Sair");
            System.out.print("Escolha: ");

            int choice = scanner.nextInt();

            if (choice == 0) break;

            System.out.print("Valor: R$");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            try {
                PaymentStrategy strategy = PaymentFactory.createPaymentStrategy(choice);
                processor.setPaymentStrategy(strategy);
                processor.process(amount);
            } catch (IllegalArgumentException e) {
                System.out.println("Opção inválida");
            }
        }
        scanner.close();
    }
}