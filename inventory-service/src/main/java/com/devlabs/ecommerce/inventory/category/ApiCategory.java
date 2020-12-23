package com.devlabs.ecommerce.inventory.category;

import com.devlabs.ecommerce.inventory.core.validation.OnCreate;
import com.devlabs.ecommerce.inventory.core.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@AllArgsConstructor
public class ApiCategory {
	
	@Null(groups = OnCreate.class)
	@NotNull(groups = OnUpdate.class)
	@Schema(description = "Category id", example = "1")
	private final Long id;
	@NotBlank(groups = { OnCreate.class, OnUpdate.class })
	@Schema(description = "Category name", example = "Keyboards")
	private final String name;
}
