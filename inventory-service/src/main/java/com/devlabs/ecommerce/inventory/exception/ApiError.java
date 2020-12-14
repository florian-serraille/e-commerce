package com.devlabs.ecommerce.inventory.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ApiError {
	
	private final HttpStatus status;
	private final ZonedDateTime timestamp;
	private final String message;
	private final List<String> details;
}
