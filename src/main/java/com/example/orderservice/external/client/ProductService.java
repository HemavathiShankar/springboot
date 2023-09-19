package com.example.orderservice.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="PRODUCT-SERVICE/product")
public interface ProductService {
	
	@PutMapping("/reduceQuantiy/{id}")
	public ResponseEntity<Void> reduceQuantity(@PathVariable long id,@RequestParam long quantity);
	@PutMapping("/increaseQuantity/{id}/{quantity")
	public ResponseEntity<Void> increaseQuantity(@PathVariable long id,@PathVariable long quanity);
		
}
