package com.azasuhaza.productservice.util;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.azasuhaza.productservice.model.Product;
import com.azasuhaza.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
	
	private final ProductRepository prodRepo;
	
	@Override
	public void run(String... args) throws Exception {
		
		if(prodRepo.count() <1) {
			
			Product p = new Product();
			p.setProductName("cake");
			p.setProductDescr("butter cake");
			p.setPrice(BigDecimal.valueOf(100));
			
			prodRepo.save(p);
		}
		
	}

}
