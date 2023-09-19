package com.example.orderservice.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

@Table(name = "order_db")
public class Order {
	
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private long id;
@Column(name = "product_id")
private long productId;
@Column(name="quantity")
private long quantity;
@Column(name="order_date")
private Instant orderDate;
@Column(name="order_status")
private String orderStatus;
@Column(name="amount")
private long amount;
public Order(long productId, long quantity, Instant orderDate, String orderStatus, long amount) {
	super();
	this.productId = productId;
	this.quantity = quantity;
	this.orderDate = orderDate;
	this.orderStatus = orderStatus;
	this.amount = amount;
}


}
