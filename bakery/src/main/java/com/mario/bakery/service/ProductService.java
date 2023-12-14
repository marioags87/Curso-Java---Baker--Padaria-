package com.mario.bakery.service;

import com.mario.bakery.model.Product;
import com.mario.bakery.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  @Autowired
  private ProductRepository repository;

  public List<Product> findAll() {
    return repository.findAll();
  }

  public Optional<Product> findById(Long id) {
    return repository.findById(id);
  }

  public Product add(Product product) {
    return repository.save(product);
  }

  public Product update(Long id, Product product) {
    product.setId(id);
    return repository.save(product);
  }

  public void delete(Long id) {
    repository.deleteById(id);
  }
}
