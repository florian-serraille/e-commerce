package com.devlabs.ecommerce.inventory.product;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static com.devlabs.ecommerce.inventory.brand.ApiBrandCatalog.getExistingBrandValid;
import static com.devlabs.ecommerce.inventory.brand.ApiBrandCatalog.getUnknownBrand;
import static com.devlabs.ecommerce.inventory.category.ApiCategoryCatalog.getExistingCategoryValid;
import static com.devlabs.ecommerce.inventory.category.ApiCategoryCatalog.getUnknownCategory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiProductCatalog {
	
	static ApiProduct getNewProductValid() {
		return new ApiProduct(null, "A new product", "A new description", BigDecimal.TEN,
		                      getExistingBrandValid(), getExistingCategoryValid());
	}
	
	static ApiProduct getExistingProductValid() {
		return new ApiProduct(1L, "A existing product", "A existing description", new BigDecimal("1898.5"),
		                      getExistingBrandValid(), getExistingCategoryValid());
	}
	
	static ApiProduct getNewProductNoBrand() {
		return new ApiProduct(null, "A new product", "A new description", BigDecimal.TEN,
		                      null, getExistingCategoryValid());
	}
	
	static ApiProduct getNewProductNoCategory() {
		return new ApiProduct(null, "A new product", "A new description", BigDecimal.TEN,
		                      getExistingBrandValid(), null);
	}
	
	static ApiProduct getNewProductCategoryNotExist() {
		return new ApiProduct(null, "A new product", "A new description", BigDecimal.TEN,
		                      getExistingBrandValid(), getUnknownCategory());
	}
	
	static ApiProduct getNewProductBrandNotExist() {
		return new ApiProduct(null, "A new product", "A new description", BigDecimal.TEN,
		                      getUnknownBrand(), getExistingCategoryValid());
	}
	
	static ApiProduct getUnknownProduct() {
		return new ApiProduct(9L, "A new product", "A new description", BigDecimal.TEN,
		                      getUnknownBrand(), getUnknownCategory());
	}
	
	static ApiProduct getExistingBrandNotExist() {
		return new ApiProduct(1L, "A new product", "A new description", BigDecimal.TEN,
		                      getUnknownBrand(), getExistingCategoryValid());
	}
	
	 static ApiProduct getExistingCategoryNotExist() {
		 return new ApiProduct(1L, "A new product", "A new description", BigDecimal.TEN,
		                       getExistingBrandValid(), getUnknownCategory());
	 }
}