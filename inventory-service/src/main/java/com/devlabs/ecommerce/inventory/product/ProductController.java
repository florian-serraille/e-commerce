package com.devlabs.ecommerce.inventory.product;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/products", produces = MediaType.APPLICATION_JSON_VALUE)
class ProductController {
	
	private final ProductService productService;
	
	@GetMapping
	ResponseEntity<List<Product>> findAllProducts() {
		return ResponseEntity.ok(productService.findAll());
	}
	
	@GetMapping("/{productId}")
	ResponseEntity<Product> findProductById(@PathVariable Long productId) {
		return ResponseEntity.ok(productService.findById(productId));
	}
	
	@PostMapping(consumes = APPLICATION_JSON_VALUE)
	ResponseEntity<Product> saveProduct(@Valid @RequestBody Product product) {
		return ResponseEntity.ok(productService.save(product));
	}
}
