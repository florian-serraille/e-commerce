package com.devlabs.ecommerce.inventory.category;

import com.devlabs.ecommerce.inventory.brand.ApiBrand;
import com.devlabs.ecommerce.inventory.brand.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
}
