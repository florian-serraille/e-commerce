package com.devlabs.ecommerce.inventory.category;

import com.devlabs.ecommerce.inventory.brand.ApiBrand;
import com.devlabs.ecommerce.inventory.brand.BrandMapper;
import com.devlabs.ecommerce.inventory.brand.BrandRepository;
import com.devlabs.ecommerce.inventory.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;
	
	List<ApiCategory> findAll() {
		return categoryMapper.toDTO(categoryRepository.findAll());
	}
	
	
	ApiCategory findById(final Long categoryId) {
		return categoryMapper.toDTO(categoryRepository.findById(categoryId)
		                                            .orElseThrow(() -> new ResourceNotFoundException(
				                                            "Category not found for id: " + categoryId)));
	}
}
