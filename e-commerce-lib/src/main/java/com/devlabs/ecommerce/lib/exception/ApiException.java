package com.devlabs.ecommerce.lib.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public abstract class ApiException extends RuntimeException {
	
	
	public ApiException(final String message) {
		super(message);
	}
	
	abstract ApiError toApiError();
}
