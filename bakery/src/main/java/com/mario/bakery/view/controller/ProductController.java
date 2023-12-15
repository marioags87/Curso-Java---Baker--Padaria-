package com.mario.bakery.view.controller;

import com.mario.bakery.model.Product;
import com.mario.bakery.service.ProductService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  @Autowired
  private ProductService service;

  @GetMapping
  public List<Product> findAll() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public Optional<Product> findById(@PathVariable Long id) {
    return service.findById(id);
  }

  @PostMapping
  public Product add(@RequestBody Product product) {
    return service.add(product);
  }

  @PutMapping("/{id}")
  public Product update(@PathVariable Long id, @RequestBody Product product) {
    return service.update(id, product);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }
}
