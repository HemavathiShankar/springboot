package com.example.orderservice.model;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderBody {

private long productId;
private long amount;
private long quantity;
private PaymentMode paymentMode;
}
