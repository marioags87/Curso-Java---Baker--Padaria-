package com.mario.bakery.view.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mario.bakery.model.Product;
import com.mario.bakery.model.exception.ResponseException;
import com.mario.bakery.service.ProductService;
import com.mario.bakery.shared.ProductRecord;
import com.mario.bakery.shared.ResponseRecord;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService service;
    
    @GetMapping
    public ResponseEntity<Object> findAll()
    {
        try {
            List<Product> products = service.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(products);
        } catch (ResponseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id)
    {
        try {
            Optional<Product> product = service.findById(id);
            if(product.isPresent())
            {
                return ResponseEntity.status(HttpStatus.OK).body(product.get());
            }
            else
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
            }
        } catch (ResponseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Product product)
    {
        try {
            Product savedProduct = service.add(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseRecord> update(@PathVariable Long id, @RequestBody ProductRecord productRecord)
    {
        return service.findById(id).map( entity -> {
            try {
                Product product = convert(productRecord);
                product.setId(entity.getId());
                product.setName(entity.getName());
                product.setDescription(entity.getDescription());
                service.update(product);
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseRecord(product,""));
            }
            catch (ResponseException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseRecord(null, e.getMessage()));
            }
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseRecord(null, "Product not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id)
    {
        return service.findById(id).map( entity -> {
            try {
                service.delete(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product deleted successfully");
            } catch (ResponseException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found"));
    }

    private Product convert(ProductRecord obj) 
    {
        Product product = new Product();
        product.setQuantity(obj.quantity());
        product.setPrice(obj.price());
        return product;
    }
}