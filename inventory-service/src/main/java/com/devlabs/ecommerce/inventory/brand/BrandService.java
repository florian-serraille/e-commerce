package com.devlabs.ecommerce.inventory.brand;

import com.devlabs.ecommerce.inventory.exception.ResourceNotFoundException;
import com.devlabs.ecommerce.inventory.product.ApiProduct;
import com.devlabs.ecommerce.inventory.product.ProductMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BrandService {

	private final BrandRepository brandRepository;
	private final BrandMapper brandMapper;
	
	List<ApiBrand> findAll() {
		return brandMapper.toDTO(brandRepository.findAll());
	}
	
	
	ApiBrand findById(final Long brandId) {
		return brandMapper.toDTO(brandRepository.findById(brandId)
		                                            .orElseThrow(() -> new ResourceNotFoundException(
				                                            "Brand not found for id: " + brandId)));
	}
}
