package com.example.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.orderservice.model.OrderBody;
import com.example.orderservice.model.OrderResponse;
import com.example.orderservice.service.OrderService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/order")
@Log4j2
public class OrderController {
	 @Autowired
	 private OrderService service;
	 
	 @PostMapping("/placeOrder")
	 public ResponseEntity<String> placeOrder(@RequestBody OrderBody ordReq){
		 System.out.println(ordReq);
		 long orderId=service.placeOrder(ordReq);
		 log.info("order id"+orderId);
		return new ResponseEntity<>("order placed "+orderId,HttpStatus.OK);
		 
	 }
	 @GetMapping("/getOrderDetails/{orderId}")
	 public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable long orderId){
		 OrderResponse orderResponse=service.getOrderDetails(orderId);
		return new ResponseEntity<>(orderResponse,HttpStatus.FOUND);
				 
	 }
	

}
