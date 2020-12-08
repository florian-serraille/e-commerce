package com.devlabs.ecommerce.inventory.controller;

import com.devlabs.ecommerce.inventory.model.Product;
import com.devlabs.ecommerce.inventory.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {
	
	private final ProductService productService;
	
	@GetMapping
	public ResponseEntity<List<Product>> findAllProducts() {
		return ResponseEntity.ok(productService.findAll());
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<Product> findProductById(@PathVariable Long productId) {
		return ResponseEntity.ok(productService.findById(productId));
	}
	
	@PostMapping(consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
		return ResponseEntity.ok(productService.save(product));
	}
}
