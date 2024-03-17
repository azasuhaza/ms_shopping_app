package com.azasuhaza.ordersvc.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsDto {

	private Long id;
	private String skuCode;
	private BigDecimal price;
	private Integer quantity;
	
}
