package com.openwt.urlshortener.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.openwt.urlshortener.dto.ErrorDTO;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	 @ExceptionHandler(Exception.class)
	 public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {

	  ErrorDTO errorDetails = new ErrorDTO(ex.getMessage(), request.getDescription(false));

	  return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);

	 }
}
