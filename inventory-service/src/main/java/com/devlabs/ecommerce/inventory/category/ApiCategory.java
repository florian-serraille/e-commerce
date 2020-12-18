package com.devlabs.ecommerce.inventory.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class ApiCategory {
	
	@Schema(description = "Category id", example = "1")
	private final Long id;
	@NotBlank
	@Schema(description = "Category name", example = "Keyboards")
	private final String name;
}
