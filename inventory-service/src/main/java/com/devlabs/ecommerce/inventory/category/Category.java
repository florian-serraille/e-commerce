package com.devlabs.ecommerce.inventory.category;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Entity
@NoArgsConstructor
@SequenceGenerator(name = "CATEGORY_SEQ", sequenceName = "CATEGORY_SEQ", allocationSize = 1)
public class Category {
	
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "CATEGORY_SEQ")
	private Long id;
	@NotBlank
	private String name;
}
