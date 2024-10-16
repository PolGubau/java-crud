package com.test.app.crud.services;

import java.util.List;
import java.util.Optional;

import com.test.app.crud.entities.Product;

public interface ProductService {

    List<Product> getAll();

    Optional<Product> findById(Long id);

    Product save(Product product);

    Optional<Product> delete(Long productId);
}
