package com.devlabs.ecommerce.inventory.category;

import com.devlabs.ecommerce.inventory.category.ApiCategory;

public class ApiCategoryCatalog {
	
	public static ApiCategory getExistingCategoryValid(){
		return new ApiCategory(2L, "A category");
	}
	
	static ApiCategory getNewCategoryValid() {
		return new ApiCategory(null, "A category");
	}
	
	static ApiCategory getNewCategoryNoName() {
		return new ApiCategory(null, null);
	}
	
	static ApiCategory getExistingCategoryNoName() {
		return new ApiCategory(1L, null);
	}
	
	public static ApiCategory getUnknownCategory() {
		return new ApiCategory(99999L, "Unknown category");
	}
}