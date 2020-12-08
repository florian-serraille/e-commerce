package com.devlabs.ecommerce.lib.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public abstract class ApiException extends RuntimeException {
	
	private final String devMessage;
	
	public ApiException(final String userMessage, final String devMessage) {
		
		super(userMessage);
		this.devMessage = devMessage;
	}
	
	public void debugLog(){
		log.error(devMessage);
	}
}
