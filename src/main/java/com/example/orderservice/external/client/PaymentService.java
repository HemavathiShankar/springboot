package com.example.orderservice.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.orderservice.model.PaymentRequest;


@FeignClient(name="PAYMENT-SERVICE/payment")
public interface PaymentService {
	@PostMapping("/doPayment")
	public ResponseEntity<String> doPayment(@RequestBody PaymentRequest payment);

}
