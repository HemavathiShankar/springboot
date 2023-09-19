package com.example.orderservice.service;

import com.example.orderservice.model.OrderBody;
import com.example.orderservice.model.OrderResponse;

public interface OrderService {

	long placeOrder(OrderBody ordReq);

	OrderResponse getOrderDetails(long orderId);

}
