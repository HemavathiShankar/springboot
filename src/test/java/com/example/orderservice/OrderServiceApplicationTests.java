package com.example.orderservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.orderservice.entity.Order;
import com.example.orderservice.external.client.PaymentService;
import com.example.orderservice.external.client.ProductService;
import com.example.orderservice.external.exceptions.CustomOrderException;
import com.example.orderservice.model.OrderBody;
import com.example.orderservice.model.OrderResponse;
import com.example.orderservice.model.PaymentMode;
import com.example.orderservice.model.PaymentRequest;
import com.example.orderservice.model.ProductBody;
import com.example.orderservice.repo.OrderRepo;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.service.OrderServiceImpl;


@SpringBootTest
class OrderServiceApplicationTests {

	@Mock
	private OrderRepo repo;
	
	@Mock
	private ProductService productService;
	
	@Mock
	private PaymentService paymentService;
	
	@Mock
	private RestTemplate restTemplate;
	
	@InjectMocks
	OrderService service=new OrderServiceImpl();
	
	@Test
	void get_order_test_success() {
		Order order=getMockOrder();
		ProductBody pro=mockProductResponse();
		PaymentRequest pay=mockPaymentReq();
		//mocking
		when(repo.findById(anyLong())).thenReturn(java.util.Optional.of(order));
		
		when(restTemplate.getForObject("http://PRODUCT-SERVICE/product/getProducts/"+order.
				getProductId(),ProductBody.class)).thenReturn(pro);
		
		when(restTemplate.getForObject("http://PAYMENT-SERVICE/payment/getPaymentByOrderId/"
		+order.getId(),PaymentRequest.class)).thenReturn(pay);
		//actual
		OrderResponse ordBody=service.getOrderDetails(1);
		//verification
		verify(repo,times(1)).findById(anyLong());
		verify(restTemplate,times(1)).getForObject("http://PRODUCT-SERVICE/product/getProducts/"+order.
				getProductId(),ProductBody.class);
		verify(restTemplate,times(1)).getForObject("http://PAYMENT-SERVICE/payment/getPaymentByOrderId/"
				+order.getId(),PaymentRequest.class);
		//assertiion
		assertNotNull(ordBody);
		assertEquals(order.getId(), ordBody.getOrderId());
		
		
		
	}

	private PaymentRequest mockPaymentReq() {
		// TODO Auto-generated method stub
		return PaymentRequest.builder().amount(2000).paymentMode(PaymentMode.CASH).orderId(1).build();
	}

	private ProductBody mockProductResponse() {
		// TODO Auto-generated method stub
		return ProductBody.builder().price(1000).quantity(5).productName("vivo").productId(101).build();
	}

	private Order getMockOrder() {
		// TODO Auto-generated method stub
		return Order.builder().amount(1000).productId(101).
				orderDate(Instant.now()).orderStatus("placed").quantity(5).build();
	}
	
	@Test
	void order_Not_found_failure_scenario() {
		
		when(repo.findById(anyLong())).thenReturn(Optional.ofNullable(null));
		assertThrows(CustomOrderException.class,()->service.getOrderDetails(1) );
	}
	
	@Test
	void place_order_success_scenario() {
		OrderBody body=mockOrderBody();
		Order order=getMockOrder();
		when(repo.save(any(Order.class))).thenReturn(order);
		when(productService.reduceQuantity(anyLong(), anyLong())).
		thenReturn(new ResponseEntity<>(HttpStatus.OK));
		when(paymentService.doPayment(any(PaymentRequest.class))).
		thenReturn(new ResponseEntity<>("payment competed successfully",HttpStatus.CREATED));
		//when(productService.increaseQuantity(anyLong(), anyLong())).
		//thenReturn(new ResponseEntity<>(HttpStatus.OK));
		long orderId=service.placeOrder(body);
		verify(repo,times(2)).save(any());
		verify(productService,times(1)).reduceQuantity(anyLong(), anyLong());
		//verify(productService,times(1)).increaseQuantity(anyLong(), anyLong());
		verify(paymentService,times(1)).doPayment(any(PaymentRequest.class));
		assertEquals(orderId, order.getId());
	}
	
	@Test
	void test_when_place_order_payment_fails_then_order_placed() {
		OrderBody body=mockOrderBody();
	Order order=getMockOrder();
	when(repo.save(any(Order.class))).thenReturn(order);
	when(productService.reduceQuantity(anyLong(), anyLong())).
	thenReturn(new ResponseEntity<>(HttpStatus.OK));
	when(paymentService.doPayment(any(PaymentRequest.class))).
	thenThrow(new RuntimeException());
	when(productService.increaseQuantity(anyLong(), anyLong())).
  thenReturn(new ResponseEntity<>(HttpStatus.OK));
	long orderId=service.placeOrder(body);
	verify(repo,times(2)).save(any());
			verify(productService,times(1)).reduceQuantity(anyLong(), anyLong());
	verify(productService,times(1)).increaseQuantity(anyLong(), anyLong());
	verify(paymentService,times(1)).doPayment(any(PaymentRequest.class));
	assertEquals(orderId, order.getId());
	
		
	}
		
		
		
		
		


	private OrderBody mockOrderBody() {
		// TODO Auto-generated method stub
		return OrderBody.builder().amount(2000).
				paymentMode(PaymentMode.CASH).productId(101).quantity(5).build();
	}
		
		
		
		


}
