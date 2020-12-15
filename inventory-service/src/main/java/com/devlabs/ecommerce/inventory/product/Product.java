package com.devlabs.ecommerce.inventory.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
class Product {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Schema(example = "1")
	private Long id;
	@NotBlank
	@Schema(example = "Product name")
	private String name;
	@NotNull
	@PositiveOrZero
	@Schema(example = "10.0")
	@JsonFormat(shape= JsonFormat.Shape.STRING)
	private BigDecimal price;
}