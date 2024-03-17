package com.azasuhaza.productservice.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.azasuhaza.productservice.dto.ProductRequest;
import com.azasuhaza.productservice.dto.ProductResponse;
import com.azasuhaza.productservice.exception.DBException;
import com.azasuhaza.productservice.exception.NullException;
import com.azasuhaza.productservice.exception.ProductServiceException;
import com.azasuhaza.productservice.model.Product;
import com.azasuhaza.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

	private final ProductRepository repo;
	
	public void createProduct(ProductRequest productRequest) {
		
		if (productRequest.equals(null)) {
			log.error("product request is null");
			throw new NullException("600", "unable to create new product coz product is null");
		}
		
		try {
			Product product = Product.builder()
					.productName(productRequest.getProductName())
					.productDescr(productRequest.getProductDescr())
					.price(productRequest.getPrice())
					.build();		
			repo.save(product);
			log.info("Product with ID: {} is saved", product.getId());
		} catch(IllegalArgumentException e) {
			throw new ProductServiceException ("601", "illegal argument supplied " + e.getMessage());
		} catch (Exception e) {
			throw new ProductServiceException ("602", "error at service layer- please check " + e.getMessage());
		}
	}

	public ProductResponse getProductById(String id) {
		Product product =null;
		if(id.isBlank() || id.isEmpty() | id.equals(null)) {
			throw new NullException("603", "unable to get product coz product id is null");
		}
		
		try {
			product = repo.findById(id).get();
		} catch(NoSuchElementException e) {
			throw new DBException ("604", "error at DB layer- please check " + e.getMessage());
		}
		
		return mapToProducDTORes(product);
	}

	public List<ProductResponse> getAllProductsInCatalog() {
		
		 List<Product> products = null;
		 
		 try {
			 products= repo.findAll();
			 
		 } catch(Exception e) {
			 throw new ProductServiceException("606", "anable to fetch all products");
		 }
			 
			if(products.isEmpty()) {
				throw new ProductServiceException("608","ProductService exception- products list is empty ");
			}		 
		return products.stream().map(this::mapToProducDTORes).collect(Collectors.toList());
	}
	
	private ProductResponse mapToProducDTORes(Product product) {
		return ProductResponse.builder()
				.id(product.getId())
				.productName(product.getProductName())
				.productDescr(product.getProductDescr())
				.price(product.getPrice())
				.build();
		
	}

	public void deleteProduct(String id) {
		if(id.isBlank() || id.isEmpty() || id.equals(null)) {
			throw new NullException("605", "unable to delete, id is not provided");
		}
		
		repo.deleteById(id);
	}


	public ProductResponse updateProduct(String id, ProductRequest productRequest) {
		
		Product prod= Product.builder()
				.price(productRequest.getPrice())
				.productDescr(productRequest.getProductDescr())
				.productName(productRequest.getProductName())
				.build();
		
		return mapToProducDTORes(prod);
	}


}
