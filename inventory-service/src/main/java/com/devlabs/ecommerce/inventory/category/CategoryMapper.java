package com.devlabs.ecommerce.inventory.category;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
	
	List<ApiCategory> toDTO(List<Category> categories);
	
	ApiCategory toDTO(Category ca);
}
