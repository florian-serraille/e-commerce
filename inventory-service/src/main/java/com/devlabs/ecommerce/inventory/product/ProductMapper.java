package com.devlabs.ecommerce.inventory.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ProductMapper {
	
	Product toModel(ApiProduct apiProduct);
	List<Product> toModel(List<ApiProduct> apiProduct);
	
	ApiProduct toDTO(Product product);
	List<ApiProduct> toDTO(List<Product> product);
}
