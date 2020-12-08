package com.devlabs.ecommerce.lib.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
public abstract class RestExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	private ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex) {
		
		ex.debugLog();
		final ApiError apiError = ex.toApiError();
		
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}