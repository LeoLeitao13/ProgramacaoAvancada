package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
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

            switch (choice) {
                case 1: processor.setPaymentStrategy(new PixPayment()); break;
                case 2: processor.setPaymentStrategy(new CreditCardPayment()); break;
                case 3: processor.setPaymentStrategy(new BoletoPayment()); break;
                default: System.out.println("Opção inválida"); continue;
            }

            processor.process(amount);
        }
        scanner.close();
    }
}