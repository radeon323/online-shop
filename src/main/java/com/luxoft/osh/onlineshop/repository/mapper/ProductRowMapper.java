package com.luxoft.osh.onlineshop.repository.mapper;

import com.luxoft.osh.onlineshop.entity.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


public class ProductRowMapper implements RowMapper<Product> {
    public Product mapRow(ResultSet resultSet, int numRow) throws SQLException {
        int id  = resultSet.getInt("id");
        String name = resultSet.getString("name").trim();
        double price  = resultSet.getDouble("price");
        Timestamp creationDate = resultSet.getTimestamp("creation_date");
        Product product = Product.builder().
                id(id)
                .name(name)
                .price(price)
                .creationDate(creationDate.toLocalDateTime())
                .build();
    return product;
    }
}
