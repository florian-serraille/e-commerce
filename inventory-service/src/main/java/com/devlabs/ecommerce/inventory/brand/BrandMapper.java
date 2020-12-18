package com.devlabs.ecommerce.inventory.brand;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface BrandMapper {
	
	List<ApiBrand> toDTO(List<Brand> brands);
	
	ApiBrand toDTO(Brand brand);
}
