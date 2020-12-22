package com.devlabs.ecommerce.inventory.category;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface CategoryMapper {
	
	List<ApiCategory> toDTO(List<Category> categories);
	
	ApiCategory toDTO(Category ca);
	
	Category toModel(ApiCategory apiCategory);
	
	@Mapping(target = "id", ignore = true)
	Category updateModel(@MappingTarget Category category, ApiCategory apiCategory);
}
