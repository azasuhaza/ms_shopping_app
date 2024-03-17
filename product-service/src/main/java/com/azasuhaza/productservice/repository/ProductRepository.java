package com.azasuhaza.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.azasuhaza.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{

}
