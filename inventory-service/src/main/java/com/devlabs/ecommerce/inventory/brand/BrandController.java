package com.devlabs.ecommerce.inventory.brand;

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

import static com.devlabs.ecommerce.inventory.core.openapi.ApiConfig.Path.BRANDS;
import static com.devlabs.ecommerce.inventory.core.openapi.ApiConfig.PathVariable.BRAND_ID;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@Tag(name = "Brands management", description = "Retrieve and Update brands")
@RequestMapping(value = BRANDS, produces = APPLICATION_JSON_VALUE)
public class BrandController {
	
	private final BrandService brandService;
	
	@GetMapping
	@Operation(summary = "List all brands", description = "List all brands available")
	public ResponseEntity<List<ApiBrand>> findAllBrands() {
		return ResponseEntity.ok(brandService.findAll());
	}
	
	@GetMapping(BRAND_ID)
	@Operation(summary = "Find a brand", description = "Find a brand by his ID")
	public ResponseEntity<ApiBrand> findBrandById(@PathVariable Long brandId) {
		return ResponseEntity.ok(brandService.findById(brandId));
	}
	
	@PostMapping(consumes = APPLICATION_JSON_VALUE)
	@Operation(summary = "Register a brand", description = "Register a brand with all his fields")
	@ApiResponse(description = "Created", responseCode = "201")
	public ResponseEntity<ApiBrand> saveBrand(@Valid @RequestBody ApiBrand apiBrand) {
		
		final ApiBrand persistedBrand = brandService.save(apiBrand);
		final UriComponents uriComponents = UriComponentsBuilder.fromPath(BRANDS + BRAND_ID)
		                                                        .buildAndExpand(persistedBrand.getId());
		
		return ResponseEntity.created(uriComponents.toUri()).body(persistedBrand);
	}
	
	@PutMapping(value = BRAND_ID, consumes = APPLICATION_JSON_VALUE)
	@Operation(summary = "Update a brand", description = "Update a brand with all his fields")
	public ResponseEntity<ApiBrand> updateBrand(@PathVariable Long brandId, @Valid @RequestBody ApiBrand apiBrand) {
		return ResponseEntity.ok(brandService.update(brandId, apiBrand));
	}
	
	@DeleteMapping(BRAND_ID)
	@Operation(summary = "Delete a brand", description = "Delete a brand by his ID")
	@ApiResponse(description = "No content", responseCode = "204")
	public ResponseEntity<Void> deleteBrand(@PathVariable Long brandId) {
		
		brandService.delete(brandId);
		return ResponseEntity.noContent().build();
	}
}
