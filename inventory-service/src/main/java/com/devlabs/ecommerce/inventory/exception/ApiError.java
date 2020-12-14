package com.devlabs.ecommerce.inventory.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ApiError {
	
	@Schema(example = "200 OK")
	private final HttpStatus status;
	private final ZonedDateTime timestamp;
	@Schema(example = "Main cause of error")
	private final String message;
	@Schema(example = "Error's detail")
	private final List<String> details;
}
