package com.devlabs.ecommerce.inventory.product;

import com.devlabs.ecommerce.inventory.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
class ProductService {
	
	private final ProductRepository productRepository;
	private final ProductMapper productMapper;
	
	List<ApiProduct> findAll() {
		return productMapper.toDTO(productRepository.findAll());
	}
	
	ApiProduct findById(final Long productId) {
		return productMapper.toDTO(productRepository.findById(productId)
		                                            .orElseThrow(() -> new ResourceNotFoundException(
				                                            "Product not found for id: " + productId)));
	}
	
	ApiProduct save(final ApiProduct apiProduct) {
		
		final Product product = productMapper.toModel(apiProduct);
		return productMapper.toDTO(productRepository.save(product));
	}
}
