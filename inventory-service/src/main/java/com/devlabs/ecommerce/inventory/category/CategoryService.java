package com.devlabs.ecommerce.inventory.category;

import com.devlabs.ecommerce.inventory.core.exception.ResourceInUseException;
import com.devlabs.ecommerce.inventory.core.exception.ResourceNotFoundException;
import com.devlabs.ecommerce.inventory.core.validation.OnCreate;
import com.devlabs.ecommerce.inventory.core.validation.OnUpdate;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Validated
@AllArgsConstructor
public class CategoryService {
	
	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;
	
	List<ApiCategory> findAll() {
		return categoryMapper.toDTO(categoryRepository.findAll());
	}
	
	public ApiCategory findById(@NotNull final Long categoryId) {
		return categoryMapper.toDTO(findModelById(categoryId));
	}
	
	public Category findModelById(@NotNull final Long categoryId) {
		return categoryRepository.findById(categoryId)
		                         .orElseThrow(() -> new ResourceNotFoundException(
				                         "Category not found for id: " + categoryId));
	}
	
	@Validated(OnCreate.class)
	ApiCategory save(@Valid final ApiCategory apiCategory) {
		
		final Category category = categoryMapper.toModel(apiCategory);
		return categoryMapper.toDTO(categoryRepository.save(category));
	}
	
	@Validated(OnUpdate.class)
	ApiCategory update(@NotNull final Long categoryId, @Valid final ApiCategory apiCategory) {
		
		final Category category = findModelById(categoryId);
		final Category updatedCategory = categoryMapper.updateModel(category, apiCategory);
		return categoryMapper.toDTO(categoryRepository.save(updatedCategory));
	}
	
	void delete(@NotNull final Long categoryId) {
		
		final Category category = findModelById(categoryId);
		
		try {
			categoryRepository.delete(category);
			
		} catch (DataIntegrityViolationException exception) {
			throw new ResourceInUseException("Error to delete brand for id " + categoryId + ". " +
			                                 "Still some product(s) using this category");
		}
	}
}
