package com.unicesumar.entities;

import com.unicesumar.paymentMethods.PaymentType;

import java.util.List;
import java.util.UUID;

public class Sale extends Entity {
    private final UUID userId;
    private final List<Product> products;
    private final double total;
    private final PaymentType paymentMethod;

    public Sale(UUID userId, List<Product> products, double total, PaymentType paymentMethod) {
        this.userId = userId;
        this.products = products;
        this.total = total;
        this.paymentMethod = paymentMethod;
    }

    public Sale(UUID uuid, UUID userId, List<Product> products, double total, PaymentType paymentMethod) {
        super(uuid);
        this.userId = userId;
        this.products = products;
        this.total = total;
        this.paymentMethod = paymentMethod;
    }

    public UUID getUserId() {
        return userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getTotal() {
        return total;
    }

    public PaymentType getPaymentMethod() {
        return paymentMethod;
    }

    @Override
    public String toString() {
        return String.format("Venda: Usuário ID: %s, Produtos: %s, Total: R$ %.2f, Pagamento: %s",
                userId, products, total, paymentMethod);
    }
}
