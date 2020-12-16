package com.devlabs.ecommerce.inventory.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

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
public
class Product {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	@NotBlank
	private String name;
	@NotNull
	@PositiveOrZero
	private BigDecimal price;
}