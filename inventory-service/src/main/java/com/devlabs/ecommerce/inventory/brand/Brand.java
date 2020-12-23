package com.devlabs.ecommerce.inventory.brand;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Entity
@SequenceGenerator(name = "BRAND_SEQ", sequenceName = "BRAND_SEQ", allocationSize = 1)
public class Brand {
	
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "BRAND_SEQ")
	private Long id;
	@NotBlank
	private String name;
}
