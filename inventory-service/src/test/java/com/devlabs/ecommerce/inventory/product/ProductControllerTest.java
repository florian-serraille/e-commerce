package com.devlabs.ecommerce.inventory.product;

import com.devlabs.ecommerce.lib.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductService productService;
	
	@Test
	void findAllProducts() throws Exception {
		
		// Given
		List<Product> expectedProducts = ProductCatalog.getProductList();
		Mockito.when(productService.findAll()).thenReturn(expectedProducts);
		
		// When
		final ResultActions actions = mockMvc.perform(get("/api/v1/products"));
		
		// Then
		actions.andExpect(status().isOk())
		       .andExpect(jsonPath("$", hasSize(expectedProducts.size())))
		
		       .andExpect(jsonPath("$[0].id", is(expectedProducts.get(0).getId().intValue())))
		       .andExpect(jsonPath("$[0].name", is(expectedProducts.get(0).getName())))
		       .andExpect(jsonPath("$[0].price", equalTo(expectedProducts.get(0).getPrice().toString())))
		
		       .andExpect(jsonPath("$[1].id", is(expectedProducts.get(1).getId().intValue())))
		       .andExpect(jsonPath("$[1].name", is(expectedProducts.get(1).getName())))
		       .andExpect(jsonPath("$[1].price", equalTo(expectedProducts.get(1).getPrice().toString())))
		
		       .andExpect(jsonPath("$[2].id", is(expectedProducts.get(2).getId().intValue())))
		       .andExpect(jsonPath("$[2].name", is(expectedProducts.get(2).getName())))
		       .andExpect(jsonPath("$[2].price", equalTo(expectedProducts.get(2).getPrice().toString())));
		
	}
	
	@Test
	void findProductById_ThenSuccess() throws Exception {
		
		// Given
		Product expectedProduct = ProductCatalog.getProductWithId();
		Mockito.when(productService.findById(expectedProduct.getId())).thenReturn(expectedProduct);
		
		// When
		final ResultActions actions = mockMvc.perform(get("/api/v1/products/" + expectedProduct.getId()));
		
		// Then
		actions.andExpect(status().isOk())
		       .andExpect(jsonPath("$.id", is(expectedProduct.getId().intValue())))
		       .andExpect(jsonPath("$.name", is(expectedProduct.getName())))
		       .andExpect(jsonPath("$.price", equalTo(expectedProduct.getPrice().toString())));
	}
	
	@Test
	void findProductById_ThenError() throws Exception {
		
		// Given
		Mockito.when(productService.findById(anyLong())).thenThrow(new ResourceNotFoundException("Product not found"));
		
		// When
		final ResultActions actions = mockMvc.perform(get("/api/v1/products/1"));
		
		// Then
		actions.andExpect(status().isNotFound());
	}
	
	@Test
	void saveProduct() throws Exception {
		
		// Given
		final ObjectMapper mapper = new ObjectMapper();
		final Product expectedProduct = ProductCatalog.getProductWithoutId();
		Mockito.when(productService.save(expectedProduct))
		       .thenReturn(ProductCatalog.getProductWithId());
		
		// When
		final ResultActions actions = mockMvc.perform(post("/api/v1/products")
				                                              .contentType(MediaType.APPLICATION_JSON_VALUE)
				                                              .content(mapper.writeValueAsString(expectedProduct)));
		
		// Then
		actions.andExpect(status().isOk())
		       .andExpect(jsonPath("$.id", notNullValue()))
		       .andExpect(jsonPath("$.name", is(expectedProduct.getName())))
		       .andExpect(jsonPath("$.price", equalTo(expectedProduct.getPrice().toString())));
	}
	
	@Test
	void saveProductWithConstraintsViolation() throws Exception {
		
		// Given
		final ObjectMapper mapper = new ObjectMapper();
		final Product expectedProduct = ProductCatalog.getProductWithConstraintViolation();
		
		// When
		final ResultActions actions = mockMvc.perform(post("/api/v1/products")
				                                              .contentType(MediaType.APPLICATION_JSON_VALUE)
				                                              .content(mapper.writeValueAsString(expectedProduct)));
		
		// Then
		actions.andExpect(status().isBadRequest());
		Mockito.verifyNoInteractions(productService);
	}
}