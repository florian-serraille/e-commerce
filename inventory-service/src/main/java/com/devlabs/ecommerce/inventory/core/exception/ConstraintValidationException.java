package com.devlabs.ecommerce.inventory.core.exception;

import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ConstraintValidationException extends ApiException {
	
	private final List<String> contraintErrors;
	
	public ConstraintValidationException(final List<FieldError> fieldErrors) {
		
		super("Validation error");
		this.contraintErrors = fieldErrors.stream()
		                                  .map(error -> "Validation error on field '" + error.getField() + "' :" +
		                                                "rejected value [" + error.getRejectedValue() + "]. " +
		                                                "Cause: " + error.getDefaultMessage())
		                                  .collect(Collectors.toList());
	}
	
	public ConstraintValidationException(final Set<ConstraintViolation<?>> constraintViolations) {
		super("Validation error");
		this.contraintErrors = constraintViolations.stream()
		                                           .map(constraint -> "Validation error on field '" + getLastField(constraint.getPropertyPath()) + "' :" +
		                                                              "rejected value [" + constraint.getInvalidValue() + "]. " +
		                                                              "Cause: " + constraint.getMessage())
		                                           .collect(Collectors.toList());
	}
	
	private String getLastField(final Path propertyPath) {
		
		final Iterator<Path.Node> iterator = propertyPath.iterator();
		String property = "";
		
		while (iterator.hasNext()){
			property = iterator.next().getName();
		}
		
		return property;
	}
	
	@Override
	public ApiError toApiError() {
		return new ApiError(BAD_REQUEST, ZonedDateTime.now(), getMessage(), contraintErrors);
	}
}
