package com.devlabs.ecommerce.inventory.exception;

import com.devlabs.ecommerce.inventory.brand.Brand;

import java.time.ZonedDateTime;
import java.util.Collections;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ResourceInUseException extends ApiException {
	
	public ResourceInUseException(final String message) {
		super(message);
	}
	
	@Override
	ApiError toApiError() {
		return new ApiError(CONFLICT, ZonedDateTime.now(),
		                    "Resource conflict",
		                    Collections.singletonList(this.getMessage()));
	}
}
