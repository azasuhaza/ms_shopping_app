package com.azasuhaza.ordersvc.event;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderPlacedEvent extends ApplicationEvent {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderNumber;
	 
	    public OrderPlacedEvent(Object source, String orderNumber) {
	        super(source);
	        this.orderNumber = orderNumber;
	    }

	    public OrderPlacedEvent(String orderNumber) {
	        super(orderNumber);
	        this.orderNumber = orderNumber;
	    }	 
	 
}
