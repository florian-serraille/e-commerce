package com.devlabs.ecommerce.inventory.category;

import com.devlabs.ecommerce.inventory.product.ApiProduct;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@Tag(name = "Categories management", description = "Retrieve and Update categories")
@RequestMapping(value = "/api/v1/inventory/categories", produces = APPLICATION_JSON_VALUE)
public class CategoryController {
	
	private final CategoryService categoryService;
	
	@GetMapping
	@Operation(summary = "List all categories", description = "List all categories available")
	ResponseEntity<List<ApiCategory>> findAllCategories() {
		return ResponseEntity.ok(categoryService.findAll());
	}
	
	@GetMapping("/{categoryId}")
	@Operation(summary = "Find a category", description = "Find a category by his ID")
	ResponseEntity<ApiCategory> findCategoryById(@PathVariable Long categoryId) {
		return ResponseEntity.ok(categoryService.findById(categoryId));
	}
	
	@PostMapping(consumes = APPLICATION_JSON_VALUE)
	@Operation(summary = "Register a category", description = "Register a category with all his fields")
	ResponseEntity<ApiCategory> saveProduct(@Valid @RequestBody ApiCategory apiBrand) {
		
		final ApiCategory persistedBrand = categoryService.save(apiBrand);
		final UriComponents uriComponents = UriComponentsBuilder.fromPath("/api/v1/inventory/brands/{productId}")
		                                                        .buildAndExpand(persistedBrand.getId());
		
		return ResponseEntity.created(uriComponents.toUri()).body(persistedBrand);
	}
	
	@PutMapping(value = "/{categoryId}", consumes = APPLICATION_JSON_VALUE)
	@Operation(summary = "Update a category", description = "Update a category with all his fields")
	ResponseEntity<ApiCategory> updateProduct(@PathVariable Long categoryId, @Valid @RequestBody ApiCategory apiBrand) {
		return ResponseEntity.ok(categoryService.update(categoryId, apiBrand));
	}
	
	@DeleteMapping("/{categoryId}")
	@Operation(summary = "Delete a category", description = "Delete a category by his ID")
	ResponseEntity<ApiProduct> deleteBrand(@PathVariable Long categoryId) {
		
		categoryService.delete(categoryId);
		return ResponseEntity.noContent().build();
	}
}
