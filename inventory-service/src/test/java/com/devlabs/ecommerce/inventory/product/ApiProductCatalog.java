package com.devlabs.ecommerce.inventory.product;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiProductCatalog {
	
	public static List<ApiProduct> getProductList() {
		
		ApiProduct p1 = new ApiProduct(1L, "Pen", BigDecimal.valueOf(1));
		ApiProduct p2 = new ApiProduct(2L, "Book", BigDecimal.valueOf(10));
		ApiProduct p3 = new ApiProduct(3L, "BackPack", BigDecimal.valueOf(50));
		
		return Arrays.asList(p1, p2, p3);
	}
	
	public static ApiProduct getProductWithId() {
		return new ApiProduct(1L, "Pen", BigDecimal.valueOf(1));
	}
	
	public static ApiProduct getProductWithoutId() {
		return new ApiProduct(null, "Pen", BigDecimal.valueOf(1));
	}
	
	public static ApiProduct getProductWithConstraintViolation() {
		return new ApiProduct(null, "", BigDecimal.valueOf(-1));
	}
}