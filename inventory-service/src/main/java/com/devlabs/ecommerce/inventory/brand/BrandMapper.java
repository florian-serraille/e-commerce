package com.devlabs.ecommerce.inventory.brand;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface BrandMapper {
	
	List<ApiBrand> toDTO(List<Brand> brands);
	
	ApiBrand toDTO(Brand brand);
	
	Brand toModel(ApiBrand apiBrand);
	
	@Mapping(target = "id", ignore = true)
	Brand updateModel(@MappingTarget Brand brand, ApiBrand apiBrand);
}
