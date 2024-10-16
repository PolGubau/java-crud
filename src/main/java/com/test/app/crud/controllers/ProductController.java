package com.test.app.crud.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.app.crud.entities.Product;
import com.test.app.crud.services.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    ProductController(ProductService productService) {
        this.service = productService;
    }

    @GetMapping
    public List<Product> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Optional<Product> product = service.findById(id);
        if (!product.isPresent())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(product.orElseThrow());

    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        Product newProduct = service.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        Optional<Product> existingProduct = service.findById(id);
        if (!existingProduct.isPresent())
            return ResponseEntity.notFound().build();

        product.setId(id);
        Product updatedProduct = service.save(product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable Long id) {
        Optional<Product> product = service.delete(id);
        if (!product.isPresent())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(product.orElseThrow());

    }

}
