package com.devlabs.ecommerce.inventory.product;

import com.devlabs.ecommerce.inventory.product.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductCatalog {

	public static List<Product> getProductList() {

		Product p1 = new Product(1L, "Pen", BigDecimal.valueOf(1));
		Product p2 = new Product(2L, "Book", BigDecimal.valueOf(10));
		Product p3 = new Product(3L, "BackPack", BigDecimal.valueOf(50));

		return Arrays.asList(p1, p2, p3);
	}

	public static Product getProductWithId() {
		return new Product(1L, "Pen", BigDecimal.valueOf(1));
	}
}