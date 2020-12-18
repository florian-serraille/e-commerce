package com.devlabs.ecommerce.inventory.brand;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class ApiBrand {
	
	@Schema(description = "Brand id", example = "1")
	private final Long id;
	@NotBlank
	@Schema(description = "Brand name", example = "Keychron")
	private final String name;
}
