package com.devlabs.ecommerce.inventory.core.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ApiException extends RuntimeException {
	
	
	protected ApiException(final String message) {
		super(message);
	}
	
	abstract ApiError toApiError();
}
