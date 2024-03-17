package com.azasuhaza.productservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.azasuhaza.productservice.exception.NullException;
import com.azasuhaza.productservice.exception.ProductServiceException;

@ControllerAdvice
public class ProductControllerAdvice {

	@ExceptionHandler(NullException.class)
	public ResponseEntity<String> handleNull(NullException nullException){
		
		return new ResponseEntity<String>("product is null", HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(ProductServiceException.class)
	public ResponseEntity<String> handleServiceError(ProductServiceException productServiceException){
		return new ResponseEntity<String>("error at service layer", HttpStatus.BAD_REQUEST);
	}
}
