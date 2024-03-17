package com.azasuhaza.ordersvc.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.azasuhaza.ordersvc.dto.OrderRequest;
import com.azasuhaza.ordersvc.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
	
	private final OrderService service;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@CircuitBreaker(name="inventory", fallbackMethod= "failedOrder")
	@TimeLimiter(name="inventory")
	@Retry(name="inventory")
	public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orederReq){
		log.info("placing new order");
		
		return CompletableFuture.supplyAsync(()-> service.placeOrder(orederReq));
	}

	public CompletableFuture<String> failedOrder(OrderRequest orderRequest, RuntimeException ex){
		log.info("cannot place order- inventory issue");
		return CompletableFuture.supplyAsync(()-> "something went wrong. please try later " 
		+ ex.getMessage());
	}
}



