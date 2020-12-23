package com.devlabs.ecommerce.inventory.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

import static com.devlabs.ecommerce.inventory.core.openApi.ApiConfig.Path.PRODUCTS;
import static com.devlabs.ecommerce.inventory.core.openApi.ApiConfig.PathVariable.PRODUCT_ID;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@Tag(name = "Products management", description = "Create Retrieve Update and Delete products")
@RequestMapping(value = PRODUCTS, produces = APPLICATION_JSON_VALUE)
class ProductController {
	
	private final ProductService productService;
	
	@GetMapping
	@Operation(summary = "List all products", description = "List all products available")
	ResponseEntity<List<ApiProduct>> findAllProducts() {
		return ResponseEntity.ok(productService.findAll());
	}
	
	@GetMapping(PRODUCT_ID)
	@Operation(summary = "Find a product", description = "Find a product by his ID")
	ResponseEntity<ApiProduct> findProductById(@PathVariable Long productId) {
		return ResponseEntity.ok(productService.findById(productId));
	}
	
	@PostMapping(consumes = APPLICATION_JSON_VALUE)
	@Operation(summary = "Register a product", description = "Register a product with all his fields")
	@ApiResponse(description = "Created", responseCode = "201")
	ResponseEntity<ApiProduct> saveProduct(@Valid @RequestBody ApiProduct product) {
		
		final ApiProduct persistedProduct = productService.save(product);
		final UriComponents uriComponents = UriComponentsBuilder.fromPath(PRODUCTS + PRODUCT_ID)
		                                                        .buildAndExpand(persistedProduct.getId());
		
		return ResponseEntity.created(uriComponents.toUri()).body(persistedProduct);
	}
	
	@DeleteMapping(PRODUCT_ID)
	@Operation(summary = "Delete a product", description = "Delete a product by his ID")
	@ApiResponse(description = "No content", responseCode = "204")
	ResponseEntity<ApiProduct> deleteProduct(@PathVariable Long productId) {
		
		productService.delete(productId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(PRODUCT_ID)
	@Operation(summary = "Update a product", description = "Delete a product by his ID")
	ResponseEntity<ApiProduct> updateProduct(@PathVariable Long productId, @Valid @RequestBody ApiProduct apiProduct) {
		return ResponseEntity.ok(productService.update(productId, apiProduct));
	}
}
