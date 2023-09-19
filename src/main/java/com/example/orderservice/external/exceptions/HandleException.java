package com.example.orderservice.external.exceptions;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandleException {
	@ExceptionHandler(CustomOrderException.class)
	public ResponseEntity<ErrorMessage> handleCustomNotFound(CustomOrderException e){
		ErrorMessage em=new ErrorMessage(e.getMessage(),e.getErrorCode());
		return new ResponseEntity<>(em,HttpStatus.valueOf(e.getStatus()));
		
	}

}
