package com.devlabs.ecommerce.inventory.product;

import com.devlabs.ecommerce.inventory.brand.ApiBrand;
import com.devlabs.ecommerce.inventory.category.ApiCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class ApiProduct {
	
	@Schema(description = "Product id", example = "1")
	private final Long id;
	@NotBlank
	@Schema(description = "Product name", example = "K8")
	private final String name;
	@NotBlank
	@Schema(description = "Product description", example = "Wireless Mechanical Keyboard")
	private final String description;
	@NotNull
	@PositiveOrZero
	@Schema(description = "Price in dollar", example = "69.0")
	@JsonFormat(shape= JsonFormat.Shape.STRING)
	private final BigDecimal unitPrice;
	private final ApiBrand brand;
	private final ApiCategory category;
	
}
