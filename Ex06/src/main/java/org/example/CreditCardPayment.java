package org.example;
import java.util.Scanner;

public class CreditCardPayment implements PaymentStrategy {
    @Override
    public void processPayment(double amount) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o número do cartão: ");
        scanner.nextLine();
        System.out.println("Cartão de R$" + amount + " processado");
    }
}