package com.devlabs.ecommerce.inventory.brand;

import com.devlabs.ecommerce.inventory.core.validation.OnCreate;
import com.devlabs.ecommerce.inventory.core.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Getter
@AllArgsConstructor
public class ApiBrand {
	
	@Null(groups = OnCreate.class)
	@Schema(description = "Brand id", example = "1")
	private final Long id;
	@NotBlank(groups = { OnCreate.class, OnUpdate.class })
	@Schema(description = "Brand name", example = "Keychron")
	private final String name;
}
