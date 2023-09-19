package com.example.orderservice.external.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomOrderException extends RuntimeException{
	
	private String errorCode;
	private int status;
	 public CustomOrderException(String message,String errorCode, int status) {
		 super(message);
		 this.errorCode=errorCode;
		 this.status=status;
	 }

}
