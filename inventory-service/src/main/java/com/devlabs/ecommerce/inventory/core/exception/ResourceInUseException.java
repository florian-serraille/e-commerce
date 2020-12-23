package com.devlabs.ecommerce.inventory.core.exception;

import java.time.ZonedDateTime;
import java.util.Collections;

import static org.springframework.http.HttpStatus.CONFLICT;

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
