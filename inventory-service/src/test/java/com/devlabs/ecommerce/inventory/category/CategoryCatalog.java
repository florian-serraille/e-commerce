package com.devlabs.ecommerce.inventory.category;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryCatalog {

	public static Category getCategory(){
		return new Category(1L, "A category");
	}
}