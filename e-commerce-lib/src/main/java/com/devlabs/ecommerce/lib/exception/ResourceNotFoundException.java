package com.devlabs.ecommerce.lib.exception;

import java.time.ZonedDateTime;
import java.util.Collections;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ResourceNotFoundException extends ApiException {
	
	public ResourceNotFoundException(final String message) {
		super(message);
	}
	
	@Override
	public ApiError toApiError() {
		
		return new ApiError(NOT_FOUND, ZonedDateTime.now(),
		                    "Resource not found",
		                    Collections.singletonList(this.getMessage()));
	}
}
