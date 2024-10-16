package com.test.app.crud.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.app.crud.entities.Product;
import com.test.app.crud.services.ProductService;
import com.test.app.crud.validations.FieldErrorDetails;

import jakarta.validation.Valid;

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
    public ResponseEntity<?> create(
            @Valid @RequestBody Product product,
            BindingResult result) {

        if (result.hasFieldErrors()) {
            return validation(result);
        }
        Product newProduct = service.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @Valid @RequestBody Product product,
            BindingResult result,
            @PathVariable Long id) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
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

    private ResponseEntity<?> validation(BindingResult result) {

        // Mapa que contendrá los errores
        Map<String, FieldErrorDetails> errors = new HashMap<>();

        // Recorrer los errores y rellenar el mapa
        result.getFieldErrors().forEach((error) -> {
            // Crear un nuevo FieldErrorDetails para cada error
            FieldErrorDetails errorDetails = new FieldErrorDetails(
                    error.getDefaultMessage(), // El mensaje de error
                    error.getRejectedValue() // El valor rechazado (recibido)
            );

            // Añadirlo al mapa con el nombre del campo como clave
            errors.put(error.getField(), errorDetails);
        });

        // Devolver el mapa de errores en el cuerpo de la respuesta
        return ResponseEntity.badRequest().body(errors);
    }
}
