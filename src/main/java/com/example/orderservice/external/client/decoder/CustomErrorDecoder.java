package com.example.orderservice.external.client.decoder;

import java.io.IOException;

import com.example.orderservice.external.exceptions.CustomOrderException;
import com.example.orderservice.external.exceptions.ErrorMessage;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;
@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {
	
	

	@Override
	public Exception decode(String methodKey, Response response) {
		ObjectMapper objectMapper=new ObjectMapper();
		log.info("response url"+response.request().url());
		log.info("::{}"+response.request().headers());
		
		try {
			ErrorMessage err=objectMapper.readValue(response.body().asInputStream(),ErrorMessage.class);
			return new CustomOrderException(err.getMessage(),err.getErrorCode(),response.status());
		} 
		catch (IOException e) {
		throw new CustomOrderException("Internal server error","INTERBAL_SERVER_ERROR",500);
		}
		

}}
