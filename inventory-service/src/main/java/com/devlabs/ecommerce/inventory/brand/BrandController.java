package com.devlabs.ecommerce.inventory.brand;

import com.devlabs.ecommerce.inventory.product.ApiProduct;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
	
	@PostMapping(consumes = APPLICATION_JSON_VALUE)
	@Operation(summary = "Register a brand", description = "Register a brand with all his fields")
	@ApiResponse(description = "Created", responseCode = "201")
	ResponseEntity<ApiBrand> saveProduct(@Valid @RequestBody ApiBrand apiBrand) {
		
		final ApiBrand persistedBrand = brandService.save(apiBrand);
		final UriComponents uriComponents = UriComponentsBuilder.fromPath("/api/v1/inventory/brands/{productId}")
		                                                        .buildAndExpand(persistedBrand.getId());
		
		return ResponseEntity.created(uriComponents.toUri()).body(persistedBrand);
	}
	
	@PutMapping(value = "/{brandId}", consumes = APPLICATION_JSON_VALUE)
	@Operation(summary = "Update a brand", description = "Update a brand with all his fields")
	ResponseEntity<ApiBrand> updateProduct(@PathVariable Long brandId, @Valid @RequestBody ApiBrand apiBrand) {
		return ResponseEntity.ok(brandService.update(brandId, apiBrand));
	}
	
	@DeleteMapping("/{brandId}")
	@Operation(summary = "Delete a brand", description = "Delete a brand by his ID")
	@ApiResponse(description = "No content", responseCode = "204")
	ResponseEntity<ApiProduct> deleteBrand(@PathVariable Long brandId) {
		
		brandService.delete(brandId);
		return ResponseEntity.noContent().build();
	}
}
