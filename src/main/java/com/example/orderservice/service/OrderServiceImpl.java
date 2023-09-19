package com.example.orderservice.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.orderservice.entity.Order;
import com.example.orderservice.external.client.PaymentService;
import com.example.orderservice.external.client.ProductService;
import com.example.orderservice.external.exceptions.CustomOrderException;
import com.example.orderservice.model.OrderBody;
import com.example.orderservice.model.OrderResponse;
import com.example.orderservice.model.PaymentRequest;
import com.example.orderservice.model.ProductBody;
import com.example.orderservice.repo.OrderRepo;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepo repo;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	/*
	 *  order Entity -> save the data with status order created; // product
	 * Service-> blocks products(reduce the quantity) // payment
	 * service->payments->success->complete else cancelled
	 */	
	@Override
	public long placeOrder(OrderBody ordReq) {
	
		log.info("placing the order:{} calling product service "+ordReq);
		// calling product service Api
		productService.reduceQuantity(ordReq.getProductId(), ordReq.getQuantity());
		
		log.info("order request placess successfully with status created");
		Order order = Order.builder().amount(ordReq.getAmount()).productId(ordReq.getProductId())
				.quantity(ordReq.getQuantity()).orderStatus("created").orderDate(Instant.now()).build();
		System.out.println(ordReq);
		//Order order=new Order(ordReq.getProductId(),ordReq.getQuantity(),Instant.now(),"created",ordReq.getTotalAmount());
		long id = repo.save(order).getId();
		log.info("order place successfully" + id);
		
		log.info("calling payment service to complete the payment");
		PaymentRequest paymentRequest=PaymentRequest.builder()
				.orderId(order.getId()).paymentMode(ordReq.getPaymentMode()).amount(ordReq.getAmount()).build();
 String orderStatus=null;
		try {
			paymentService.doPayment(paymentRequest);
			log.info("payment  done succesfully changing orderstatus from created to placed");
			orderStatus="ORDER_PLACED";
		}
		catch(Exception e) {
			log.error("error occured in payment, changing order status to failed");
			orderStatus="PAYMENT_FAILED";
			productService.increaseQuantity(ordReq.getProductId(), ordReq.getAmount());
		}
		order.setOrderStatus(orderStatus);
		repo.save(order);
		log.info("order placed successfuly your orderstatur "+orderStatus+" and order id "+id);
		return id;
	}

	@Override
	public OrderResponse getOrderDetails(long orderId) {
		// TODO Auto-generated method stub
		Order order=repo.findById(orderId).
				orElseThrow(()-> new CustomOrderException("order not found","ORDER_NOT_FOUND",404));
		log.info("Invoking product service to invoke product details based on product Id");
		ProductBody product=restTemplate.getForObject("http://PRODUCT-SERVICE/product/getProducts/"+order.
				getProductId(),ProductBody.class);
		log.info("invoking payment service to get payment details");
		PaymentRequest payment=restTemplate.getForObject("http://PAYMENT-SERVICE/payment/getPaymentByOrderId/"+order.getId(),PaymentRequest.class);
	 OrderResponse response=new OrderResponse(order.getId(),order.getOrderDate(),order.getOrderStatus(),order.getAmount(),product,payment);
		return response;
	}

}
