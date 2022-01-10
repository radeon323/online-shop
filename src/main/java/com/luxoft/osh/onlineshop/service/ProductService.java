package com.luxoft.osh.onlineshop.service;

import com.luxoft.osh.onlineshop.entity.Product;
import com.luxoft.osh.onlineshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }


    public void add(Product product) {
        product.setCreationDate(LocalDateTime.now());
        productRepository.add(product);
        System.out.println("Add product " + product.getName());
    }

    public void remove(int id) {
        productRepository.remove(id);
        System.out.println("Remove product with id " + id);
    }

    public void edit(Product product) {
        productRepository.edit(product);
    }

    public Product productFindById(int id) {
        return productRepository.productFindById(id);
    }
}
