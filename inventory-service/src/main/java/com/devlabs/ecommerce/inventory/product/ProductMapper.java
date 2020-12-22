package com.devlabs.ecommerce.inventory.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface ProductMapper {
	
	Product toModel(ApiProduct apiProduct);
	
	List<Product> toModel(List<ApiProduct> products);
	
	ApiProduct toDTO(Product product);
	
	List<ApiProduct> toDTO(List<Product> product);
	
	@Mapping(target = "id", ignore = true)
	Product updateModel(@MappingTarget Product product, ApiProduct apiProduct);
}
