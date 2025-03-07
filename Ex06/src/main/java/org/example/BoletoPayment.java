package org.example;

import java.util.Random;

public class BoletoPayment implements PaymentStrategy {
    @Override
    public void processPayment(double amount) {
        String boletoCode = String.format("%010d", new Random().nextInt(1000000000));
        System.out.println("Boleto de R$" + amount + " - Código: " + boletoCode);
    }
}