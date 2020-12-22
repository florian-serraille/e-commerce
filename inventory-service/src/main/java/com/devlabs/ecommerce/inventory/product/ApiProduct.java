package com.devlabs.ecommerce.inventory.product;

import com.devlabs.ecommerce.inventory.brand.ApiBrand;
import com.devlabs.ecommerce.inventory.category.ApiCategory;
import com.devlabs.ecommerce.inventory.validation.OnCreate;
import com.devlabs.ecommerce.inventory.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class ApiProduct {
	
	@Null(groups = OnCreate.class)
	@NotNull(groups = OnUpdate.class)
	@Schema(description = "Product id", example = "1")
	private final Long id;
	@NotBlank(groups = { OnCreate.class, OnUpdate.class })
	@Schema(description = "Product name", example = "K8")
	private final String name;
	@NotBlank(groups = { OnCreate.class, OnUpdate.class })
	@Schema(description = "Product description", example = "Wireless Mechanical Keyboard")
	private final String description;
	@NotNull(groups = { OnCreate.class, OnUpdate.class })
	@PositiveOrZero(groups = { OnCreate.class, OnUpdate.class })
	@Schema(description = "Price in dollar", example = "69.0")
	@JsonFormat(shape= JsonFormat.Shape.STRING)
	private final BigDecimal unitPrice;
	private final ApiBrand brand;
	private final ApiCategory category;
	
}
