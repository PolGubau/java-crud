package com.test.app.crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.app.crud.entities.Product;
import com.test.app.crud.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

  final ProductRepository repository;

  ProductServiceImpl(ProductRepository repository) {
    this.repository = repository;
  }

  @Transactional(readOnly = true)
  @Override
  public List<Product> getAll() {
    return (List<Product>) repository.findAll();
  }

  @Transactional(readOnly = true)
  @Override
  public Optional<Product> findById(Long id) {
    return repository.findById(id);

  }

  @Transactional
  @Override
  public Product save(Product product) {
    return repository.save(product);

  }

  @Transactional
  @Override
  public Optional<Product> delete(Long productId) {
    Optional<Product> product = repository.findById(productId);
    product.ifPresent(p -> repository.delete(p));
    return product;
  }

}
