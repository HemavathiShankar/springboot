package com.example.orderservice.model;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class PaymentRequest {
	
	private long orderId;
	private long amount;
	private String referenceNumber;
	private PaymentMode paymentMode;
	

}
