package com.luxoft.osh.onlineshop.repository.jdbc;

import com.luxoft.osh.onlineshop.entity.Product;
import com.luxoft.osh.onlineshop.repository.ProductRepository;
import com.luxoft.osh.onlineshop.repository.mapper.ProductRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class JdbcProductRepository implements ProductRepository {

    private static final String ADD = "INSERT INTO products (name, price, creation_date) VALUES (:name, :price, :creation_date);";
    private static final String FIND_ALL_SQL = "SELECT id, name, price, creation_date FROM products;";
    private static final String DELETE_BY_ID = "DELETE FROM products WHERE id = ?;";
    private static final String UPDATE_BY_ID = "UPDATE products SET name = ?, price = ? WHERE id = ?;";
    private static final String FIND_BY_ID = "SELECT * FROM products WHERE id = ?";

    private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, PRODUCT_ROW_MAPPER);
    }

    @Override
    public void add(Product product) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", product.getName());
        parameters.put("price", product.getPrice());
        parameters.put("creation_date", product.getCreationDate());
        namedParameterJdbcTemplate.update(ADD, parameters);
    }

    @Override
    public void remove(int id) {
        jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public void edit(Product product) {
        jdbcTemplate.update(UPDATE_BY_ID, product.getId());

    }

    @Override
    public Product productFindById(int id) {
        return (Product) jdbcTemplate.queryForObject(FIND_BY_ID, PRODUCT_ROW_MAPPER, new Object[]{id});
    }

}
