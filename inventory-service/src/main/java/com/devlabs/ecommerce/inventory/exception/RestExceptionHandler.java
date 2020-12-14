package com.devlabs.ecommerce.inventory.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	private ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex) {
		
		final ApiError apiError = ex.toApiError();
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
		
		final ApiError apiError = new ConstraintValidationException(ex.getBindingResult().getFieldErrors()).toApiError();
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}