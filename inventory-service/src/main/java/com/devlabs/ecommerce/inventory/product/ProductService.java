package com.devlabs.ecommerce.inventory.product;

import com.devlabs.ecommerce.inventory.brand.BrandService;
import com.devlabs.ecommerce.inventory.category.CategoryService;
import com.devlabs.ecommerce.inventory.exception.ResourceNotFoundException;
import com.devlabs.ecommerce.inventory.validation.OnCreate;
import com.devlabs.ecommerce.inventory.validation.OnUpdate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Validated
@AllArgsConstructor
public
class ProductService {
	
	private final ProductRepository productRepository;
	private final ProductMapper productMapper;
	private final CategoryService categoryService;
	private final BrandService brandService;
	
	List<ApiProduct> findAll() {
		return productMapper.toDTO(productRepository.findAll());
	}
	
	ApiProduct findById(@NotNull final Long productId) {
		return productMapper.toDTO(findModelById(productId));
	}
	
	private Product findModelById(@NotNull Long productId) {
		return productRepository.findById(productId)
		                        .orElseThrow(() -> new ResourceNotFoundException(
				                        "Product not found for id: " + productId));
	}
	
	@Validated(OnCreate.class)
	ApiProduct save(@Valid final ApiProduct apiProduct) {
		
		final Product product = productMapper.toModel(apiProduct);
		product.setBrand(brandService.findModelById(apiProduct.getBrand().getId()));
		product.setCategory(categoryService.findModelById(apiProduct.getCategory().getId()));
		
		return productMapper.toDTO(productRepository.save(product));
	}
	
	void delete(final Long productId) {
		productRepository.deleteById(productId);
	}
	
	@Validated(OnUpdate.class)
	@Transactional
	public ApiProduct update(final Long productId, final ApiProduct apiProduct) {
		
		final Product product = findModelById(productId);
		
		final Product updatedProduct = productMapper.updateModel(product, apiProduct);
		updatedProduct.setBrand(brandService.findModelById(apiProduct.getBrand().getId()));
		updatedProduct.setCategory(categoryService.findModelById(apiProduct.getCategory().getId()));
		
		return productMapper.toDTO(updatedProduct);
	}
}

