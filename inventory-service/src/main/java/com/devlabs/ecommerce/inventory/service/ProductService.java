package com.devlabs.ecommerce.inventory.service;

import com.devlabs.ecommerce.inventory.model.Product;
import com.devlabs.ecommerce.lib.exception.ResourceNotFoundException;
import com.devlabs.ecommerce.inventory.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
	
	private final ProductRepository productRepository;
	
	public List<Product> findAll() {
		return productRepository.findAll();
	}
	
	public Product findById(final Long productId) {
		return productRepository.findById(productId).orElseThrow(
				() -> new ResourceNotFoundException("Product not found for id: " + productId));
	}
	
	public Product save(final Product product) {
		return productRepository.save(product);
	}
}
