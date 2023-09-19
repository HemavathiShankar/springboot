package com.example.orderservice.model;

import org.springframework.stereotype.Component;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Component
public class ProductBody {
	
	
	private long productId;
	private String productName;
	private long price;
	private long quantity;
	public ProductBody(String productName, long price, long quantity) {
		super();
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
	}
	

}
