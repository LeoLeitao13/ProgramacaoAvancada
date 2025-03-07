package org.example;

import java.util.Random;

public class PixPayment implements PaymentStrategy {
    @Override
    public void processPayment(double amount) {
        String pixCode = String.format("%06d", new Random().nextInt(1000000));
        System.out.println("Pix de R$" + amount + " - Código: " + pixCode);
    }
}