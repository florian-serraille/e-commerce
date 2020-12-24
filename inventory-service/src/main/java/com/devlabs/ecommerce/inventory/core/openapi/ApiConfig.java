package com.devlabs.ecommerce.inventory.core.openapi;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiConfig {
	
	
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Path {
		
		public static final String ROOT = "/api/v1/inventory";
		public static final String BRANDS = ROOT + "/brands";
		public static final String CATEGORIES = ROOT + "/categories";
		public static final String PRODUCTS = ROOT + "/products";
		
		
	}
	
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class PathVariable {
		
		public static final String BRAND_ID = "/{brandId}";
		public static final String CATEGORY_ID = "/{categoryId}";
		public static final String PRODUCT_ID = "/{productId}";
		
		
	}
}
