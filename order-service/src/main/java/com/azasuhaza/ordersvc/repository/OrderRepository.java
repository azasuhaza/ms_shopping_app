package com.azasuhaza.ordersvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.azasuhaza.ordersvc.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
