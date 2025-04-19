package com.unicesumar.repository;

import com.unicesumar.entities.Sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class SaleRepository implements EntityRepository<Sale> {
    Connection connection;
    ProductRepository productRepository;

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
            stmt.setString(3, String.join(";", entity.getprodutos().stream()
                    .map(p -> p.getUuid().toString())
                    .collect(Collectors.toList())));
            stmt.setDouble(4, entity.getTotal());
            stmt.setString(5, entity.getmetodoPagamento().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao registrar a venda: " + e.getMessage());
        }
    }

    @Override
    public Optional<Sale> findById(UUID id) {
        throw new UnsupportedOperationException("Método não implementado :(");
    }

    @Override
    public List<Sale> findAll() {
        throw new UnsupportedOperationException("Método não implementado :(");
    }

    @Override
    public void deleteById(UUID id) {
        throw new UnsupportedOperationException("Método não implementado :(");
    }
}