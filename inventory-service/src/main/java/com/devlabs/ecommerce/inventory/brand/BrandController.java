package com.devlabs.ecommerce.inventory.brand;

import com.devlabs.ecommerce.inventory.product.ApiProduct;
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
@Tag(name = "Brands management", description = "Retrieve and Update brands")
@RequestMapping(value = "/api/v1/inventory/brands", produces = APPLICATION_JSON_VALUE)
public class BrandController {

	private final BrandService brandService;
	
	@GetMapping
	@Operation(summary = "List all brands", description = "List all brands available")
	ResponseEntity<List<ApiBrand>> findAllBrands() {
		return ResponseEntity.ok(brandService.findAll());
	}
	
	@GetMapping("/{brandId}")
	@Operation(summary = "Find a brand", description = "Find a brand by his ID")
	ResponseEntity<ApiBrand> findBrandById(@PathVariable Long brandId) {
		return ResponseEntity.ok(brandService.findById(brandId));
	}
	
}
