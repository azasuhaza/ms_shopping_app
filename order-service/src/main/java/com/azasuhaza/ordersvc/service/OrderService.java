package com.azasuhaza.ordersvc.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azasuhaza.ordersvc.dto.InventoryResponse;
import com.azasuhaza.ordersvc.dto.OrderItemsDto;
import com.azasuhaza.ordersvc.dto.OrderRequest;
import com.azasuhaza.ordersvc.event.OrderPlacedEvent;
import com.azasuhaza.ordersvc.model.Order;
import com.azasuhaza.ordersvc.model.OrderItems;
import com.azasuhaza.ordersvc.repository.OrderRepository;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {
	
	private final OrderRepository repo;
	private final ObservationRegistry obsRegistry;
	private final ApplicationEventPublisher appEventPublisher;
	private final WebClient.Builder webClientBuilder;
	
	public String placeOrder(OrderRequest orederReq) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		order.setOrderDateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
		
		
		List<OrderItems> orderItemsList = orederReq.getOrderedItemsDto()
				.stream()
				.map(this::mapToDto)
				.toList();
		
		order.setItemListOrdered(orderItemsList);
		
        List<String> skuCodes = order.getItemListOrdered()
        		.stream()
                .map(OrderItems::getSkuCode)
                .toList();		
        
        Observation inventoryServiceObservation = Observation.createNotStarted("inventory-service-lookup",
                this.obsRegistry);
        
        inventoryServiceObservation.lowCardinalityKeyValue("call", "inventory-service");
        
        return inventoryServiceObservation.observe(() -> {
            InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                    .uri("http://inventory-service/api/inventory",
                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

            boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
                    .allMatch(InventoryResponse::isInStock);

            if (allProductsInStock) {
            	repo.save(order);
                // publish Order Placed Event
                appEventPublisher.publishEvent(new OrderPlacedEvent(this, order.getOrderNumber()));
                return "Order Placed";
            } else {
                throw new IllegalArgumentException("Product is not in stock, please try again later");
            }
        });
	}
	
	private OrderItems mapToDto(OrderItemsDto orderItemsDto) {
		OrderItems items= new OrderItems();
		items.setPrice(orderItemsDto.getPrice());
		items.setQuantity(orderItemsDto.getQuantity());
		items.setSkuCode(orderItemsDto.getSkuCode());
        return items;		
	}

}
