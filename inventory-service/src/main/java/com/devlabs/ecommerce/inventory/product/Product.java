package com.devlabs.ecommerce.inventory.product;

import com.devlabs.ecommerce.inventory.brand.Brand;
import com.devlabs.ecommerce.inventory.category.Category;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@Entity
@NoArgsConstructor
@SequenceGenerator(name = "PRODUCT_SEQ", sequenceName = "PRODUCT_SEQ", allocationSize = 1)
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