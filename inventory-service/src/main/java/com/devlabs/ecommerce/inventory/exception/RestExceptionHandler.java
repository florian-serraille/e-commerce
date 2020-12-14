package com.devlabs.ecommerce.inventory.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.ZonedDateTime;
import java.util.Collections;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {
	
	// Generic Exceptions
	
	@ExceptionHandler(Throwable.class)
	@ResponseStatus(code = INTERNAL_SERVER_ERROR)
	public ResponseEntity<ApiError> problem(final Throwable e) {
		
		final String error = "Problem occurred: " + e.getMessage();
		log.error(error, e);
		
		final ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR, ZonedDateTime.now(), error,
		                                       Collections.emptyList());
		
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(code = BAD_REQUEST)
	public ResponseEntity<ApiError> handleMissingServletRequestParameterException(
			MissingServletRequestParameterException ex) {
		
		String error = ex.getParameterName() + ", " + ex.getMessage();
		
		final ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR, ZonedDateTime.now(), error,
		                                       Collections.emptyList());
		
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
	
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	@ResponseStatus(code = UNSUPPORTED_MEDIA_TYPE)
	public ResponseEntity<ApiError> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex
	) {
		
		String unsupported = "Unsupported content type: " + ex.getContentType();
		String supported = " Supported content types: " + MediaType.toString(ex.getSupportedMediaTypes());
		
		final ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR, ZonedDateTime.now(), unsupported + supported,
		                                       Collections.emptyList());
		
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
	
	// Custom Exceptions
	
	@ResponseStatus(code = NOT_FOUND)
	@ExceptionHandler(ResourceNotFoundException.class)
	private ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex) {
		
		final ApiError apiError = ex.toApiError();
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
	
	@ResponseStatus(code = BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
		
		final ApiError apiError = new ConstraintValidationException(
				ex.getBindingResult().getFieldErrors()).toApiError();
		
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}