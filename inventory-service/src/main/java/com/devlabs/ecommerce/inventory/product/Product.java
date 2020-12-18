package com.devlabs.ecommerce.inventory.product;

import com.devlabs.ecommerce.inventory.brand.Brand;
import com.devlabs.ecommerce.inventory.category.Category;
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
	@NotBlank
	private String description;
	@NotNull
	@PositiveOrZero
	private BigDecimal unitPrice;
	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID")
	private Category category;
	@ManyToOne
	@JoinColumn(name = "BRAND_ID")
	private Brand brand;
}