package com.unicesumar.repository;

import com.unicesumar.entities.Sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors; // Import necessário

public class SaleRepository implements EntityRepository<Sale> {
    private final Connection connection;
    private final ProductRepository productRepository;

    public SaleRepository(Connection connection, ProductRepository productRepository) {
        this.connection = connection;
        this.productRepository = productRepository;
    }

    @Override
    public void save(Sale entity) {
        String query = "INSERT INTO sales (id, user_id, products, total, payment_method) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, entity.getUuid().toString());
            stmt.setString(2, entity.getUserId().toString());
            stmt.setString(3, String.join(";", entity.getProducts().stream()
                    .map(p -> p.getUuid().toString())
                    .collect(Collectors.toList()))); // Coletar o Stream em uma List
            stmt.setDouble(4, entity.getTotal());
            stmt.setString(5, entity.getPaymentMethod().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao registrar venda: " + e.getMessage());
        }
    }

    @Override
    public Optional<Sale> findById(UUID id) {
        throw new UnsupportedOperationException("Método findById ainda não implementado");
    }

    @Override
    public List<Sale> findAll() {
        throw new UnsupportedOperationException("Método findAll ainda não implementado");
    }

    @Override
    public void deleteById(UUID id) {
        throw new UnsupportedOperationException("Método deleteById ainda não implementado");
    }
}