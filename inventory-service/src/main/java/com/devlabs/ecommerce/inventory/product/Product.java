package com.devlabs.ecommerce.inventory.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name="PRODUCT_SEQ", sequenceName = "PRODUCT_SEQ", allocationSize = 1)
public class Product {
	
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "PRODUCT_SEQ")
	private Long id;
	@NotBlank
	private String name;
	@NotNull
	@PositiveOrZero
	private BigDecimal unitPrice;
}