package com.devlabs.ecommerce.inventory.product;

import com.devlabs.ecommerce.inventory.brand.ApiBrandCatalog;
import com.devlabs.ecommerce.inventory.category.ApiCategoryCatalog;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiProductCatalog {
	
	public static List<ApiProduct> getProductList() {
		
		ApiProduct p1 = new ApiProduct(1L, "Pen", "Pen", BigDecimal.valueOf(1), ApiBrandCatalog.getBrand(),
		                               ApiCategoryCatalog.getCategory());
		ApiProduct p2 = new ApiProduct(2L, "Book", "Book", BigDecimal.valueOf(10), ApiBrandCatalog.getBrand(),
		                               ApiCategoryCatalog.getCategory());
		ApiProduct p3 = new ApiProduct(3L, "BackPack", "BackPack", BigDecimal.valueOf(50), ApiBrandCatalog.getBrand(),
		                               ApiCategoryCatalog.getCategory());
		
		return Arrays.asList(p1, p2, p3);
	}
	
	public static ApiProduct getProductWithId() {
		return new ApiProduct(1L, "Pen", "Pen", BigDecimal.valueOf(1), ApiBrandCatalog.getBrand(),
		                      ApiCategoryCatalog.getCategory());
	}
	
	public static ApiProduct getProductWithoutId() {
		return new ApiProduct(null, "Pen", "Pen", BigDecimal.valueOf(1), ApiBrandCatalog.getBrand(), ApiCategoryCatalog.getCategory());
	}
	
	public static ApiProduct getProductWithConstraintViolation() {
		return new ApiProduct(null, "", null, BigDecimal.valueOf(-1), ApiBrandCatalog.getBrand(), ApiCategoryCatalog.getCategory());
	}
}