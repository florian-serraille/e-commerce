package com.devlabs.ecommerce.inventory.product;

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
	
	@Schema(example = "1")
	private final Long id;
	@NotBlank
	@Schema(example = "Product name")
	private final String name;
	@NotNull
	@PositiveOrZero
	@Schema(example = "10.0")
	@JsonFormat(shape= JsonFormat.Shape.STRING)
	private final BigDecimal unitPrice;
	
}
