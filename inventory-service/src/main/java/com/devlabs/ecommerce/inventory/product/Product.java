package com.devlabs.ecommerce.inventory.product;

import com.fasterxml.jackson.annotation.JsonFormat;
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
	private Long id;
	@NotBlank
	private String name;
	@NotNull
	@PositiveOrZero
	@JsonFormat(shape= JsonFormat.Shape.STRING)
	private BigDecimal price;
}