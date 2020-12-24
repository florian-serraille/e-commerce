package com.devlabs.ecommerce.inventory.product;

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
import static com.devlabs.ecommerce.inventory.core.openapi.ApiConfig.Path.PRODUCTS;
import static com.devlabs.ecommerce.inventory.core.openapi.ApiConfig.PathVariable.PRODUCT_ID;
import static com.devlabs.ecommerce.inventory.product.ApiProductCatalog.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class ProductIntegratedTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	@DisplayName("List all products, successful")
	void listAllProducts() throws Exception {
		
		// Given
		final URI performedURI = toUri(PRODUCTS);
		
		// When
		final ResultActions perform = mockMvc.perform(get(performedURI).contentType(MediaType.APPLICATION_JSON));
		
		// Then
		System.out.println(perform.andReturn().getResponse().getContentAsString());
		
		perform.andExpect(status().isOk())
		       .andExpect(jsonPath("$.[*]", hasSize(2)))
		
		       .andExpect(jsonPath("$.[0].id", is(1)))
		       .andExpect(jsonPath("$.[0].name", is("A product")))
		       .andExpect(jsonPath("$.[0].description", is("A description")))
		       .andExpect(jsonPath("$.[0].unitPrice", is("10.00")))
		       .andExpect(jsonPath("$.[0].brand.id", is(1)))
		       .andExpect(jsonPath("$.[0].brand.name", is("A brand")))
		       .andExpect(jsonPath("$.[0].category.id", is(1)))
		       .andExpect(jsonPath("$.[0].category.name", is("A category")))
		
		       .andExpect(jsonPath("$.[1].id", is(2)))
		       .andExpect(jsonPath("$.[1].name", is("Another product")))
		       .andExpect(jsonPath("$.[1].description", is("Another description")))
		       .andExpect(jsonPath("$.[1].unitPrice", is("20.50")))
		       .andExpect(jsonPath("$.[1].brand.id", is(1)))
		       .andExpect(jsonPath("$.[1].brand.name", is("A brand")))
		       .andExpect(jsonPath("$.[1].category.id", is(1)))
		       .andExpect(jsonPath("$.[1].category.name", is("A category")));
	}
	
	@Test
	@DisplayName("Find product by id, successful")
	void findProductsById_Return200() throws Exception {
		
		// Given
		final Long expectedId = 1L;
		final URI performedURI = toUri(PRODUCTS, PRODUCT_ID, expectedId);
		
		// When
		final ResultActions perform = mockMvc.perform(get(performedURI).accept(MediaType.APPLICATION_JSON));
		
		// Then
		perform.andExpect(status().isOk())
		       .andExpect(jsonPath("$.id", is(1)))
		       .andExpect(jsonPath("$.name", is("A product")))
		       .andExpect(jsonPath("$.description", is("A description")))
		       .andExpect(jsonPath("$.unitPrice", is("10.00")))
		       .andExpect(jsonPath("$.brand.id", is(1)))
		       .andExpect(jsonPath("$.brand.name", is("A brand")))
		       .andExpect(jsonPath("$.category.id", is(1)))
		       .andExpect(jsonPath("$.category.name", is("A category")));
	}
	
	@Test
	@DisplayName("Find product by id, product not found")
	void findProductById_Return404() throws Exception {
		
		// Given
		final Long expectedId = 5L;
		final URI performedURI = toUri(PRODUCTS, PRODUCT_ID, expectedId);
		
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
	@DisplayName("Save product, successful")
	@Transactional
	void saveProduct_ShouldReturn201() throws Exception {
		
		// Given
		final URI performedURI = toUri(PRODUCTS);
		final ApiProduct expectedNewProduct = getNewProductValid();
		
		// When
		final ResultActions perform = mockMvc.perform(post(performedURI)
				                                              .accept(MediaType.APPLICATION_JSON)
				                                              .contentType(MediaType.APPLICATION_JSON)
				                                              .content(toJson(expectedNewProduct)));
		
		// Then
		perform.andExpect(status().isCreated())
		       .andExpect(header().string(LOCATION, startsWith(PRODUCTS)))
		       .andExpect(jsonPath("$.id", notNullValue()))
		       .andExpect(jsonPath("$.name", is(expectedNewProduct.getName())))
		       .andExpect(jsonPath("$.description", is(expectedNewProduct.getDescription())))
		       .andExpect(jsonPath("$.brand.id", is(expectedNewProduct.getBrand().getId().intValue())))
		       .andExpect(jsonPath("$.category.id", is(expectedNewProduct.getCategory().getId().intValue())));
		
		Assertions.assertThat(Long.valueOf(productRepository.count())).isEqualTo(3L);
	}
	
	@Test
	@DisplayName("Save product,  not null id property")
	void saveProduct_ShouldReturn400_idNotNull() throws Exception {
		
		// Given
		final URI performedURI = toUri(PRODUCTS);
		
		// When
		final ResultActions perform = mockMvc.perform(post(performedURI)
				                                              .accept(MediaType.APPLICATION_JSON)
				                                              .contentType(MediaType.APPLICATION_JSON)
				                                              .content(toJson(getExistingProductValid())));
		
		// Then
		perform.andExpect(status().isBadRequest())
		       .andExpect(jsonPath("$.status", is(BAD_REQUEST.name())))
		       .andExpect(jsonPath("$.timestamp", notNullValue()))
		       .andExpect(jsonPath("$.message", notNullValue(String.class)))
		       .andExpect(jsonPath("$.details").isArray())
		       .andExpect(jsonPath("$.details", hasSize(1)));
		
		Assertions.assertThat(Long.valueOf(productRepository.count())).isEqualTo(2L);
	}
	
	@Test
	@DisplayName("Save product,  all constraints violated")
	void saveProduct_ShouldReturn400_AllConstraintsViolated() throws Exception {
		
		// Given
		final URI performedURI = toUri(PRODUCTS);
		
		// When
		final ResultActions perform = mockMvc.perform(post(performedURI)
				                                              .accept(MediaType.APPLICATION_JSON)
				                                              .contentType(MediaType.APPLICATION_JSON)
				                                              .content("{\"id\": 1 }"));
		
		// Then
		perform.andExpect(status().isBadRequest())
		       .andExpect(jsonPath("$.status", is(BAD_REQUEST.name())))
		       .andExpect(jsonPath("$.timestamp", notNullValue()))
		       .andExpect(jsonPath("$.message", notNullValue(String.class)))
		       .andExpect(jsonPath("$.details").isArray())
		       // Id not null, name null, description null, unitPrice null, category null, brand null
		       .andExpect(jsonPath("$.details", hasSize(6)));
		
		Assertions.assertThat(Long.valueOf(productRepository.count())).isEqualTo(2L);
	}
	
	@Test
	@DisplayName("Save product,  no category provided")
	void saveProduct_ShouldReturn400_NullCategory() throws Exception {
		
		// Given
		final URI performedURI = toUri(PRODUCTS);
		
		// When
		final ResultActions perform = mockMvc.perform(post(performedURI)
				                                              .accept(MediaType.APPLICATION_JSON)
				                                              .contentType(MediaType.APPLICATION_JSON)
				                                              .content(toJson(getNewProductNoCategory())));
		
		// Then
		perform.andExpect(status().isBadRequest())
		       .andExpect(jsonPath("$.status", is(BAD_REQUEST.name())))
		       .andExpect(jsonPath("$.timestamp", notNullValue()))
		       .andExpect(jsonPath("$.message", notNullValue(String.class)))
		       .andExpect(jsonPath("$.details").isArray())
		       .andExpect(jsonPath("$.details", hasSize(1)));
		
		Assertions.assertThat(Long.valueOf(productRepository.count())).isEqualTo(2L);
	}
	
	@Test
	@DisplayName("Save product,  no brand provided")
	void saveProduct_ShouldReturn400_NullBrand() throws Exception {
		
		// Given
		final URI performedURI = toUri(PRODUCTS);
		
		// When
		final ResultActions perform = mockMvc.perform(post(performedURI)
				                                              .accept(MediaType.APPLICATION_JSON)
				                                              .contentType(MediaType.APPLICATION_JSON)
				                                              .content(toJson(getNewProductNoBrand())));
		
		// Then
		perform.andExpect(status().isBadRequest())
		       .andExpect(jsonPath("$.status", is(BAD_REQUEST.name())))
		       .andExpect(jsonPath("$.timestamp", notNullValue()))
		       .andExpect(jsonPath("$.message", notNullValue(String.class)))
		       .andExpect(jsonPath("$.details").isArray())
		       .andExpect(jsonPath("$.details", hasSize(1)));
		
		Assertions.assertThat(Long.valueOf(productRepository.count())).isEqualTo(2L);
	}
	
	@Test
	@DisplayName("Save product, category not found")
	void saveProduct_ShouldReturn404_CategoryNotFound() throws Exception {
		
		// Given
		final URI performedURI = toUri(PRODUCTS);
		
		// When
		final ResultActions perform = mockMvc.perform(post(performedURI)
				                                              .accept(MediaType.APPLICATION_JSON)
				                                              .contentType(MediaType.APPLICATION_JSON)
				                                              .content(toJson(getNewProductCategoryNotExist())));
		
		// Then
		perform.andExpect(status().isNotFound())
		       .andExpect(jsonPath("$.status", is(NOT_FOUND.name())))
		       .andExpect(jsonPath("$.timestamp", notNullValue()))
		       .andExpect(jsonPath("$.message", notNullValue(String.class)))
		       .andExpect(jsonPath("$.details").isArray())
		       .andExpect(jsonPath("$.details", hasSize(1)));
		
		Assertions.assertThat(Long.valueOf(productRepository.count())).isEqualTo(2L);
	}
	
	@Test
	@DisplayName("Save product, brand not found")
	void saveProduct_ShouldReturn400_BrandNotFound() throws Exception {
		
		// Given
		final URI performedURI = toUri(PRODUCTS);
		
		// When
		final ResultActions perform = mockMvc.perform(post(performedURI)
				                                              .accept(MediaType.APPLICATION_JSON)
				                                              .contentType(MediaType.APPLICATION_JSON)
				                                              .content(toJson(getNewProductBrandNotExist())));
		
		// Then
		perform.andExpect(status().isNotFound())
		       .andExpect(jsonPath("$.status", is(NOT_FOUND.name())))
		       .andExpect(jsonPath("$.timestamp", notNullValue()))
		       .andExpect(jsonPath("$.message", notNullValue(String.class)))
		       .andExpect(jsonPath("$.details").isArray())
		       .andExpect(jsonPath("$.details", hasSize(1)));
		
		Assertions.assertThat(Long.valueOf(productRepository.count())).isEqualTo(2L);
	}
	
	@Test
	@DisplayName("Update product, successful")
	@Transactional
	void updateProduct_ShouldReturn200() throws Exception {
		
		// Given
		final ApiProduct modifiedProduct = ApiProductCatalog.getExistingProductValid();
		final URI performedURI = toUri(PRODUCTS, PRODUCT_ID, modifiedProduct.getId());
		
		// When
		final ResultActions perform = mockMvc.perform(put(performedURI)
				                                              .accept(MediaType.APPLICATION_JSON)
				                                              .contentType(MediaType.APPLICATION_JSON)
				                                              .content(toJson(modifiedProduct)));
		
		// Then
		perform.andExpect(status().isOk())
		       .andExpect(jsonPath("$.id", is(modifiedProduct.getId().intValue())))
		       .andExpect(jsonPath("$.name", is(modifiedProduct.getName())))
		       .andExpect(jsonPath("$.description", is(modifiedProduct.getDescription())))
		       .andExpect(jsonPath("$.unitPrice", is(modifiedProduct.getUnitPrice().toString())))
		       .andExpect(jsonPath("$.brand.id", is(modifiedProduct.getBrand().getId().intValue())))
		       .andExpect(jsonPath("$.category.id", is(modifiedProduct.getCategory().getId().intValue())));
		
		Assertions.assertThat(productRepository.getOne(modifiedProduct.getId()))
		          .extracting(Product::getId, Product::getName, Product::getDescription, Product::getUnitPrice,
		                      product -> product.getCategory().getId(), product -> product.getBrand().getId())
		          .contains(modifiedProduct.getId(), modifiedProduct.getName(), modifiedProduct.getDescription(),
		                    modifiedProduct.getUnitPrice(), modifiedProduct.getCategory().getId(),
		                    modifiedProduct.getBrand().getId());
	}
	
	@Test
	@DisplayName("Update product, product not exist")
	void updateProduct_ShouldReturn404() throws Exception {
		
		// Given
		final Long expectedId = 5L;
		final URI performedURI = toUri(PRODUCTS, PRODUCT_ID, expectedId);
		
		// When
		final ResultActions perform = mockMvc.perform(put(performedURI).accept(MediaType.APPLICATION_JSON)
		                                                               .contentType(MediaType.APPLICATION_JSON)
		                                                               .content(toJson(getUnknownProduct())));
		
		// Then
		perform.andExpect(status().isNotFound())
		       .andExpect(jsonPath("$.status", is(NOT_FOUND.name())))
		       .andExpect(jsonPath("$.timestamp", notNullValue()))
		       .andExpect(jsonPath("$.message", notNullValue(String.class)))
		       .andExpect(jsonPath("$.details").isArray())
		       .andExpect(jsonPath("$.details", hasSize(1)));
	}
	
	@Test
	@DisplayName("Update product, constraints violated")
	void updateProduct_ShouldReturn400_ConstraintsViolation() throws Exception {
		
		// Given
		final URI performedURI = toUri(PRODUCTS, PRODUCT_ID, 1L);
		
		// When
		final ResultActions perform = mockMvc.perform(put(performedURI)
				                                              .accept(MediaType.APPLICATION_JSON)
				                                              .contentType(MediaType.APPLICATION_JSON)
				                                              .content("{}"));
		
		// Then
		perform.andExpect(status().isBadRequest())
		       .andExpect(jsonPath("$.status", is(BAD_REQUEST.name())))
		       .andExpect(jsonPath("$.timestamp", notNullValue()))
		       .andExpect(jsonPath("$.message", notNullValue(String.class)))
		       .andExpect(jsonPath("$.details").isArray())
		       // Id null, name null, description null, unitPrice null, category null, brand null
		       .andExpect(jsonPath("$.details", hasSize(6)));
	}
	
	@Test
	@DisplayName("Update product, brand not exist")
	void updateProduct_ShouldReturn404_BrandNotExist() throws Exception {
		
		// Given
		ApiProduct product = ApiProductCatalog.getExistingBrandNotExist();
		final URI performedURI = toUri(PRODUCTS, PRODUCT_ID, product.getId());
		
		// When
		final ResultActions perform = mockMvc.perform(put(performedURI)
				                                              .accept(MediaType.APPLICATION_JSON)
				                                              .contentType(MediaType.APPLICATION_JSON)
				                                              .content(toJson(product)));
		
		// Then
		perform.andExpect(status().isNotFound())
		       .andExpect(jsonPath("$.status", is(NOT_FOUND.name())))
		       .andExpect(jsonPath("$.timestamp", notNullValue()))
		       .andExpect(jsonPath("$.message", notNullValue(String.class)))
		       .andExpect(jsonPath("$.details").isArray())
		       .andExpect(jsonPath("$.details", hasSize(1)));
	}
	
	@Test
	@DisplayName("Update product, category not exist")
	void updateProduct_ShouldReturn404_CategoryNotExist() throws Exception {
		
		// Given
		ApiProduct product = ApiProductCatalog.getExistingCategoryNotExist();
		final URI performedURI = toUri(PRODUCTS, PRODUCT_ID, product.getId());
		
		// When
		final ResultActions perform = mockMvc.perform(put(performedURI)
				                                              .accept(MediaType.APPLICATION_JSON)
				                                              .contentType(MediaType.APPLICATION_JSON)
				                                              .content(toJson(product)));
		
		// Then
		perform.andExpect(status().isNotFound())
		       .andExpect(jsonPath("$.status", is(NOT_FOUND.name())))
		       .andExpect(jsonPath("$.timestamp", notNullValue()))
		       .andExpect(jsonPath("$.message", notNullValue(String.class)))
		       .andExpect(jsonPath("$.details").isArray())
		       .andExpect(jsonPath("$.details", hasSize(1)));
	}
	
	@Test
	@DisplayName("Delete product, successful")
	@Transactional
	void deleteProductById_Return204() throws Exception {
		
		// Given
		final URI performedURI = toUri(PRODUCTS, PRODUCT_ID, 2L);
		
		// When
		final ResultActions perform = mockMvc.perform(delete(performedURI));
		
		// Then
		perform.andExpect(status().isNoContent());
		
		Assertions.assertThat(Long.valueOf(productRepository.count())).isEqualTo(1L);
	}
	
	@Test
	@DisplayName("Delete product, brand not exist")
	void deleteProductById_Return404() throws Exception {
		
		// Given
		final ApiProduct unknownProduct = getUnknownProduct();
		final URI performedURI = toUri(PRODUCTS, PRODUCT_ID, unknownProduct.getId());
		
		// When
		final ResultActions perform = mockMvc.perform(delete(performedURI));
		
		// Then
		perform.andExpect(status().isNotFound())
		       .andExpect(jsonPath("$.status", is(NOT_FOUND.name())))
		       .andExpect(jsonPath("$.timestamp", notNullValue()))
		       .andExpect(jsonPath("$.message", notNullValue(String.class)))
		       .andExpect(jsonPath("$.details").isArray())
		       .andExpect(jsonPath("$.details", hasSize(1)));
		
		Assertions.assertThat(Long.valueOf(productRepository.count())).isEqualTo(2L);
	}
}