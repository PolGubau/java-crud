package com.test.app.crud.repositories;

import org.springframework.data.repository.CrudRepository;

import com.test.app.crud.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
