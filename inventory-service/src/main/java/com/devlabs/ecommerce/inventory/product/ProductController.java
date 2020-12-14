package com.devlabs.ecommerce.inventory.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@Tag(name = "Products management", description = "Create Retrieve Update and Delete products")
@RequestMapping(value = "/api/v1/products", produces = APPLICATION_JSON_VALUE)
class ProductController {
	
	private final ProductService productService;
	
	@GetMapping
	@Operation(summary = "List all products", description = "List all products available")
	ResponseEntity<List<Product>> findAllProducts() {
		return ResponseEntity.ok(productService.findAll());
	}
	
	@GetMapping("/{productId}")
	@Operation(summary = "Find a product", description = "Find a product by his ID")
	ResponseEntity<Product> findProductById(@PathVariable Long productId) {
		return ResponseEntity.ok(productService.findById(productId));
	}
	
	@PostMapping(consumes = APPLICATION_JSON_VALUE)
	@Operation(summary = "Register a product", description = "Register a product with all his fields")
	ResponseEntity<Product> saveProduct(@Valid @RequestBody Product product) {
		return ResponseEntity.ok(productService.save(product));
	}
}
