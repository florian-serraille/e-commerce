package com.devlabs.ecommerce.inventory.product;

import com.devlabs.ecommerce.lib.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
class ProductService {
	
	private final ProductRepository productRepository;
	
	List<Product> findAll() {
		return productRepository.findAll();
	}
	
	Product findById(final Long productId) {
		return productRepository.findById(productId).orElseThrow(
				() -> new ResourceNotFoundException("Product not found for id: " + productId));
	}
	
	Product save(final Product product) {
		return productRepository.save(product);
	}
}
