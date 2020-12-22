package com.devlabs.ecommerce.inventory.product;

import com.devlabs.ecommerce.inventory.brand.Brand;
import com.devlabs.ecommerce.inventory.brand.BrandCatalog;
import com.devlabs.ecommerce.inventory.category.Category;
import com.devlabs.ecommerce.inventory.category.CategoryCatalog;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductCatalog {
	
	public static List<Product> getProductList() {
		
		final Brand brand = BrandCatalog.getBrand();
		final Category category = CategoryCatalog.getCategory();
		
		Product p1 = new Product(1L, "Pen", "Pen", BigDecimal.valueOf(1), category, brand);
		Product p2 = new Product(2L, "Book", "Book", BigDecimal.valueOf(10), category, brand);
		Product p3 = new Product(3L, "BackPack", "BackPack", BigDecimal.valueOf(50), category, brand);
		
		return Arrays.asList(p1, p2, p3);
	}
	
	public static Product getProductWithId() {
		return new Product(1L, "Pen", "Pen", BigDecimal.valueOf(1), CategoryCatalog.getCategory(),
		                   BrandCatalog.getBrand());
	}
}