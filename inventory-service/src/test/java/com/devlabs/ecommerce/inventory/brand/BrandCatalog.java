package com.devlabs.ecommerce.inventory.brand;

import static org.junit.jupiter.api.Assertions.*;

public class BrandCatalog {
	
	public static Brand getBrand(){
		return new Brand(1L, "A brand");
	}
	
}