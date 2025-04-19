package com.unicesumar.entities;

import com.unicesumar.paymentMethods.PaymentType;

import java.util.List;
import java.util.UUID;

public class Sale extends Entity {
    UUID userId;
    List<Product> produtos;
    double total;
    PaymentType metodoPagamento;

    public Sale(UUID userId, List<Product> produtos, double total, PaymentType metodoPagamento) {
        this.userId = userId;
        this.produtos = produtos;
        this.total = total;
        this.metodoPagamento = metodoPagamento;
    }

    public UUID getUserId() {
        return userId;
    }

    public List<Product> getprodutos() {
        return produtos;
    }

    public double getTotal() {
        return total;
    }

    public PaymentType getmetodoPagamento() {
        return metodoPagamento;
    }

    @Override
    public String toString() {
        return String.format("Venda: Usuário ID: %s, produtos: %s, Total: R$ %.2f, Pagamento: %s",
                userId, produtos, total, metodoPagamento);
    }
}
