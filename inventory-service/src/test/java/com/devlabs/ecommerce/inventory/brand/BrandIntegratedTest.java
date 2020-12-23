package com.devlabs.ecommerce.inventory.brand;

import com.devlabs.ecommerce.inventory.TestHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;

import static com.devlabs.ecommerce.inventory.TestHelper.toJson;
import static com.devlabs.ecommerce.inventory.TestHelper.toUri;
import static com.devlabs.ecommerce.inventory.brand.ApiBrandCatalog.*;
import static com.devlabs.ecommerce.inventory.core.openApi.ApiConfig.Path.BRANDS;
import static com.devlabs.ecommerce.inventory.core.openApi.ApiConfig.PathVariable.BRAND_ID;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class BrandIntegratedTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Test
	@DisplayName("List all brands, successful")
	void listAllBrands() throws Exception {
		
		// Given
		final URI performedURI = toUri(BRANDS);
		
		// When
		final ResultActions perform = mockMvc.perform(get(performedURI).contentType(MediaType.APPLICATION_JSON));
		
		// Then
		perform.andExpect(status().isOk())
		       .andExpect(jsonPath("$.[*]", hasSize(2)))
		
		       .andExpect(jsonPath("$.[0].id", is(1)))
		       .andExpect(jsonPath("$.[0].name", is("A brand")))
		
		       .andExpect(jsonPath("$.[1].id", is(2)))
		       .andExpect(jsonPath("$.[1].name", is("Another brand")));
	}
	
	@Test
	@DisplayName("Find brand by id, successful")
	void findBrandById_Return200() throws Exception {
		
		// Given
		final Long expectedId = 1L;
		final URI performedURI = toUri(BRANDS, BRAND_ID, expectedId);
		
		// When
		final ResultActions perform = mockMvc.perform(get(performedURI).accept(MediaType.APPLICATION_JSON));
		
		// Then
		perform.andExpect(status().isOk())
		       .andExpect(jsonPath("$.id", is(1)))
		       .andExpect(jsonPath("$.name", is("A brand")));
	}
	
	@Test
	@DisplayName("Find brand by id, brand not exist")
	void findBrandById_Return404() throws Exception {
		
		// Given
		final ApiBrand brand = getUnknownBrand();
		final URI performedURI = toUri(BRANDS, BRAND_ID, brand.getId());
		
		// When
		final ResultActions perform = mockMvc.perform(get(performedURI).accept(MediaType.APPLICATION_JSON));
		
		// Then
		perform.andExpect(status().isNotFound())
		       .andExpect(jsonPath("$.status", is(NOT_FOUND.name())))
		       .andExpect(jsonPath("$.timestamp", notNullValue()))
		       .andExpect(jsonPath("$.message", notNullValue(String.class)))
		       .andExpect(jsonPath("$.details").isArray())
		       .andExpect(jsonPath("$.details", hasSize(1)));
	}
	
	@Test
	@DisplayName("Save brand, successful")
	@Transactional
	void saveBrand_ShouldReturn201() throws Exception {
		
		// Given
		final URI performedURI = toUri(BRANDS);
		final ApiBrand expectedNewBrand = ApiBrandCatalog.getNewBrandValid();
		
		// When
		final ResultActions perform = mockMvc.perform(post(performedURI)
				                                              .accept(MediaType.APPLICATION_JSON)
				                                              .contentType(MediaType.APPLICATION_JSON)
				                                              .content(toJson(expectedNewBrand)));
		
		// Then
		perform.andExpect(status().isCreated())
		       .andExpect(header().string(LOCATION, startsWith(BRANDS)))
		       .andExpect(jsonPath("$.id", notNullValue()))
		       .andExpect(jsonPath("$.name", is(expectedNewBrand.getName())));
		
		Assertions.assertThat(Long.valueOf(brandRepository.count())).isEqualTo(3L);
	}
	
	@Test
	@DisplayName("Save brand,  not null id property")
	void saveBrand_ShouldReturn400_idNotNull() throws Exception {
		
		// Given
		final URI performedURI = toUri(BRANDS);
		
		// When
		final ResultActions perform = mockMvc.perform(post(performedURI)
				                                              .accept(MediaType.APPLICATION_JSON)
				                                              .contentType(MediaType.APPLICATION_JSON)
				                                              .content(toJson(getExistingBrandValid())));
		
		// Then
		perform.andExpect(status().isBadRequest())
		       .andExpect(jsonPath("$.status", is(BAD_REQUEST.name())))
		       .andExpect(jsonPath("$.timestamp", notNullValue()))
		       .andExpect(jsonPath("$.message", notNullValue(String.class)))
		       .andExpect(jsonPath("$.details").isArray())
		       .andExpect(jsonPath("$.details", hasSize(1)));
		
		Assertions.assertThat(Long.valueOf(brandRepository.count())).isEqualTo(2L);
	}
	
	@Test
	@DisplayName("Save brand, null name property")
	void saveBrand_ShouldReturn400_NameNull() throws Exception {
		
		// Given
		final URI performedURI = toUri(BRANDS);
		
		// When
		final ResultActions perform = mockMvc.perform(post(performedURI)
				                                              .accept(MediaType.APPLICATION_JSON)
				                                              .contentType(MediaType.APPLICATION_JSON)
				                                              .content(toJson(getNewBrandNoName())));
		
		// Then
		perform.andExpect(status().isBadRequest())
		       .andExpect(jsonPath("$.status", is(BAD_REQUEST.name())))
		       .andExpect(jsonPath("$.timestamp", notNullValue()))
		       .andExpect(jsonPath("$.message", notNullValue(String.class)))
		       .andExpect(jsonPath("$.details").isArray())
		       .andExpect(jsonPath("$.details", hasSize(1)));
		
		Assertions.assertThat(Long.valueOf(brandRepository.count())).isEqualTo(2L);
	}
	
	@Test
	@DisplayName("Save brand, multiple constraints violation")
	void saveBrand_ShouldReturn400_MultipleFieldsError() throws Exception {
		
		// Given
		final URI performedURI = toUri(BRANDS);
		
		// When
		final ResultActions perform = mockMvc.perform(post(performedURI)
				                                              .accept(MediaType.APPLICATION_JSON)
				                                              .contentType(MediaType.APPLICATION_JSON)
				                                              .content(toJson(getExistingBrandNoName())));
		
		// Then
		perform.andExpect(status().isBadRequest())
		       .andExpect(jsonPath("$.status", is(BAD_REQUEST.name())))
		       .andExpect(jsonPath("$.timestamp", notNullValue()))
		       .andExpect(jsonPath("$.message", notNullValue(String.class)))
		       .andExpect(jsonPath("$.details").isArray())
		       .andExpect(jsonPath("$.details", hasSize(2)));
		
		Assertions.assertThat(Long.valueOf(brandRepository.count())).isEqualTo(2L);
	}
	
	@Test
	@DisplayName("Update brand, successful")
	@Transactional
	void updateBrand_ShouldReturn200() throws Exception {
		
		// Given
		final ApiBrand modifiedBrand = getExistingBrandValid();
		final URI performedURI = toUri(BRANDS, BRAND_ID, modifiedBrand.getId());
		
		// When
		final ResultActions perform = mockMvc.perform(put(performedURI)
				                                              .accept(MediaType.APPLICATION_JSON)
				                                              .contentType(MediaType.APPLICATION_JSON)
				                                              .content(toJson(modifiedBrand)));
		
		// Then
		perform.andExpect(status().isOk())
		       .andExpect(jsonPath("$.id", is(modifiedBrand.getId().intValue())))
		       .andExpect(jsonPath("$.name", is(modifiedBrand.getName())));
		
		Assertions.assertThat(brandRepository.getOne(modifiedBrand.getId()))
		          .extracting(Brand::getName)
		          .isEqualTo(modifiedBrand.getName());
	}
	
	@Test
	@DisplayName("Update brand, brand not exist")
	void updateBrand_ShouldReturn404() throws Exception {
		
		// Given
		final ApiBrand brand = getUnknownBrand();
		final URI performedURI = toUri(BRANDS, BRAND_ID, brand.getId());
		
		// When
		final ResultActions perform = mockMvc.perform(put(performedURI)
				                                              .accept(MediaType.APPLICATION_JSON)
				                                              .contentType(MediaType.APPLICATION_JSON)
				                                              .content(toJson(brand)));
		
		// Then
		perform.andExpect(status().isNotFound())
		       .andExpect(jsonPath("$.status", is(NOT_FOUND.name())))
		       .andExpect(jsonPath("$.timestamp", notNullValue()))
		       .andExpect(jsonPath("$.message", notNullValue(String.class)))
		       .andExpect(jsonPath("$.details").isArray())
		       .andExpect(jsonPath("$.details", hasSize(1)));
	}
	
	@Test
	@DisplayName("Update brand, null name property")
	void updateBrand_ShouldReturn400_NoName() throws Exception {
		
		// Given
		final ApiBrand existingBrandNoName = getExistingBrandNoName();
		final URI performedURI = toUri(BRANDS, BRAND_ID, existingBrandNoName.getId());
		
		// When
		final ResultActions perform = mockMvc.perform(put(performedURI)
				                                              .accept(MediaType.APPLICATION_JSON)
				                                              .contentType(MediaType.APPLICATION_JSON)
				                                              .content(toJson(existingBrandNoName)));
		
		// Then
		perform.andExpect(status().isBadRequest())
		       .andExpect(jsonPath("$.status", is(BAD_REQUEST.name())))
		       .andExpect(jsonPath("$.timestamp", notNullValue()))
		       .andExpect(jsonPath("$.message", notNullValue(String.class)))
		       .andExpect(jsonPath("$.details").isArray())
		       .andExpect(jsonPath("$.details", hasSize(1)));
	}
	
	@Test
	@DisplayName("Delete brand, successful")
	@Transactional
	void deleteBrandById_Return204() throws Exception {
		
		// Given
		final URI performedURI = toUri(BRANDS, BRAND_ID, 2L);
		
		// When
		final ResultActions perform = mockMvc.perform(delete(performedURI));
		
		// Then
		perform.andExpect(status().isNoContent());
		
		Assertions.assertThat(Long.valueOf(brandRepository.count())).isEqualTo(1L);
	}
	
	@Test
	@DisplayName("Delete brand, brand not exist")
	void deleteBrandById_Return404() throws Exception {
		
		// Given
		final ApiBrand brand = getUnknownBrand();
		final URI performedURI = toUri(BRANDS, BRAND_ID, brand.getId());
		
		// When
		final ResultActions perform = mockMvc.perform(delete(performedURI));
		
		// Then
		perform.andExpect(status().isNotFound())
		       .andExpect(jsonPath("$.status", is(NOT_FOUND.name())))
		       .andExpect(jsonPath("$.timestamp", notNullValue()))
		       .andExpect(jsonPath("$.message", notNullValue(String.class)))
		       .andExpect(jsonPath("$.details").isArray())
		       .andExpect(jsonPath("$.details", hasSize(1)));
		
		Assertions.assertThat(Long.valueOf(brandRepository.count())).isEqualTo(2L);
	}
	
	@Test
	@DisplayName("Delete brand, brand in use")
	void deleteBrandById_Return409() throws Exception {
		
		// Given
		final Long expectedId = 1L;
		final URI performedURI = toUri(BRANDS, BRAND_ID, expectedId);
		
		// When
		final ResultActions perform = mockMvc.perform(delete(performedURI));
		
		// Then
		perform.andExpect(status().isConflict())
		       .andExpect(jsonPath("$.status", is(CONFLICT.name())))
		       .andExpect(jsonPath("$.timestamp", notNullValue()))
		       .andExpect(jsonPath("$.message", notNullValue(String.class)))
		       .andExpect(jsonPath("$.details").isArray())
		       .andExpect(jsonPath("$.details", hasSize(1)));
		
		Assertions.assertThat(Long.valueOf(brandRepository.count())).isEqualTo(2L);
	}
}
