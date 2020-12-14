package com.devlabs.ecommerce.inventory.exception;

import org.springframework.validation.FieldError;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ConstraintValidationException extends ApiException {
	
	private final List<String> contraintErrors;
	
	public ConstraintValidationException(final List<FieldError> fieldErrors) {
		
		super("Validation error");
		this.contraintErrors = fieldErrors.stream()
		                                  .map(error -> "Field error in object " + error.getObjectName() + " " +
		                                                "on field '" + error.getField() + "' :" +
		                                                "rejected value [ " + error.getRejectedValue() + "] -> " +
		                                                error.getDefaultMessage())
		                                  .collect(Collectors.toList());
	}
	
	@Override
	public ApiError toApiError() {
		return new ApiError(BAD_REQUEST, ZonedDateTime.now(), getMessage(), contraintErrors);
	}
}
