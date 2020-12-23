package com.devlabs.ecommerce.inventory.brand;

public class ApiBrandCatalog {
	
	public static ApiBrand getExistingBrandValid() {
		return new ApiBrand(2L, "An existing brand");
	}
	
	static ApiBrand getNewBrandValid() {
		return new ApiBrand(null, "A brand");
	}
	
	static ApiBrand getNewBrandNoName() {
		return new ApiBrand(null, null);
	}
	
	static ApiBrand getExistingBrandNoName() {
		return new ApiBrand(1L, null);
	}
	
	public static ApiBrand getUnknownBrand() {
		return new ApiBrand(99999L, "Unknown brand");
	}
}