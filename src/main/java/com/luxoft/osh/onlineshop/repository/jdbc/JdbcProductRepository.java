package com.luxoft.osh.onlineshop.repository.jdbc;

import com.luxoft.osh.onlineshop.entity.Product;
import com.luxoft.osh.onlineshop.repository.ProductRepository;
import com.luxoft.osh.onlineshop.repository.mapper.ProductRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcProductRepository implements ProductRepository {

    private static final String ADD = "INSERT INTO products (name, price, creation_date) VALUES (?, ?, ?);";
    private static final String FIND_ALL_SQL = "SELECT id, name, price, creation_date FROM products;";
    private static final String DELETE_BY_ID = "DELETE FROM products WHERE id = ?;";
    private static final String UPDATE_BY_ID = "UPDATE products SET name = ?, price = ? WHERE id = ?;";
    private static final String FIND_BY_ID = "SELECT * FROM products WHERE id = ?";

    private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();

    private final JdbcTemplate jdbcTemplate;


    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, PRODUCT_ROW_MAPPER);
    }




}
