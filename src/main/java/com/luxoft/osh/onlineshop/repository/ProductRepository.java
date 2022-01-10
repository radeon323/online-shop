package com.luxoft.osh.onlineshop.repository;

import com.luxoft.osh.onlineshop.entity.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();

    void add(Product product);

    void remove(int id);

    void edit(Product product);

    Product productFindById(int id);
}
