package com.example.demo.Repo;

import com.example.demo.Entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface productRepository extends CrudRepository<Product,Long> {
    public List<Product> findAllByType(String type);
}
