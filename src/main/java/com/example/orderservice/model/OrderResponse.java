package com.example.orderservice.model;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
	
	private long orderId;
	private Instant orderDate;
	private String orderStatus;
	private long amount;
	@Autowired
	ProductBody product;
	@Autowired
	PaymentRequest payment;
	
	

}
