package com.azasuhaza.productservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.azasuhaza.productservice.dto.ProductRequest;
import com.azasuhaza.productservice.dto.ProductResponse;
import com.azasuhaza.productservice.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService service;
	
	//create product
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createProduct(@RequestBody ProductRequest productRequest) {
		service.createProduct(productRequest);
	}
	
	// get product based on id
	@GetMapping("/{prodId}")
	@ResponseStatus(HttpStatus.FOUND)
	public ProductResponse getProductById(@PathVariable("prodId") String id) {
		return service.getProductById(id);
	}

	
	// get all products
	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	public List<ProductResponse> getAllProductsInCatalog(){
		
		return service.getAllProductsInCatalog();
	}
	
	//delete product
	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void deleteProductById(@PathVariable("id") String id) {
		service.deleteProduct(id);
	}
	
	//update product based on id
	@PutMapping("/update/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ProductResponse updateProduct(@PathVariable("id") String id, @RequestBody ProductRequest productRequest) {
		
		return service.updateProduct(id, productRequest);
	}
	
	
}
