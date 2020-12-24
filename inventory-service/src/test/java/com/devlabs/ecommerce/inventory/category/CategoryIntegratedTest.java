package com.devlabs.ecommerce.inventory.category;

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

import static com.devlabs.ecommerce.inventory.TestHelper.toUri;
import static com.devlabs.ecommerce.inventory.category.ApiCategoryCatalog.*;
import static com.devlabs.ecommerce.inventory.core.openapi.ApiConfig.Path.CATEGORIES;
import static com.devlabs.ecommerce.inventory.core.openapi.ApiConfig.PathVariable.CATEGORY_ID;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class CategoryIntegratedTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Test
	@DisplayName("List all categories, successful")
	void listAllCategories() throws Exception {
		
		// Given
		final URI performedURI = toUri(CATEGORIES);
		
		// When
		final ResultActions perform = mockMvc.perform(get(performedURI).contentType(MediaType.APPLICATION_JSON));
		
		// Then
		perform.andExpect(status().isOk())
		       .andExpect(jsonPath("$.[*]", hasSize(2)))
		
		       .andExpect(jsonPath("$.[0].id", is(1)))
		       .andExpect(jsonPath("$.[0].name", is("A category")))
		
		       .andExpect(jsonPath("$.[1].id", is(2)))
		       .andExpect(jsonPath("$.[1].name", is("Another category")));
	}
	
	@Test
	@DisplayName("Find category by id, successful")
	void findCategoryById_Return200() throws Exception {
		
		// Given
		final Long expectedId = 1L;
		final URI performedURI = toUri(CATEGORIES, CATEGORY_ID, expectedId);
		
		// When
		final ResultActions perform = mockMvc.perform(get(performedURI).accept(MediaType.APPLICATION_JSON));
		
		// Then
		perform.andExpect(status().isOk())
		       .andExpect(jsonPath("$.id", is(1)))
		       .andExpect(jsonPath("$.name", is("A category")));
	}
	
	@Test
	@DisplayName("Find category by id, category not exist")
	void findCategoryById_Return404() throws Exception {
		
		// Given
		final ApiCategory category = getUnknownCategory();
		final URI performedURI = toUri(CATEGORIES, CATEGORY_ID, category.getId());
		
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
	@DisplayName("Save category, successful")
	@Transactional
	void saveCategory_ShouldReturn201() throws Exception {
		
		// Given
		final URI performedURI = toUri(CATEGORIES);
		final ApiCategory expectedNewCategory = ApiCategoryCatalog.getNewCategoryValid();
		
		// When
		final ResultActions perform = mockMvc.perform(post(performedURI)
				                                              .accept(MediaType.APPLICATION_JSON)
				                                              .contentType(MediaType.APPLICATION_JSON)
				                                              .content(TestHelper.toJson(expectedNewCategory)));
		
		// Then
		perform.andExpect(status().isCreated())
		       .andExpect(header().string(LOCATION, startsWith(CATEGORIES)))
		       .andExpect(jsonPath("$.id", notNullValue()))
		       .andExpect(jsonPath("$.name", is(expectedNewCategory.getName())));
		
		Assertions.assertThat(Long.valueOf(categoryRepository.count())).isEqualTo(3L);
	}
	
	@Test
	@DisplayName("Save category,  not null id property")
	void saveCategory_ShouldReturn400_idNotNull() throws Exception {
		
		// Given
		final URI performedURI = toUri(CATEGORIES);
		
		// When
		final ResultActions perform = mockMvc.perform(post(performedURI)
				                                              .accept(MediaType.APPLICATION_JSON)
				                                              .contentType(MediaType.APPLICATION_JSON)
				                                              .content(TestHelper.toJson(getExistingCategoryValid())));
		
		// Then
		perform.andExpect(status().isBadRequest())
		       .andExpect(jsonPath("$.status", is(BAD_REQUEST.name())))
		       .andExpect(jsonPath("$.timestamp", notNullValue()))
		       .andExpect(jsonPath("$.message", notNullValue(String.class)))
		       .andExpect(jsonPath("$.details").isArray())
		       .andExpect(jsonPath("$.details", hasSize(1)));
		
		Assertions.assertThat(Long.valueOf(categoryRepository.count())).isEqualTo(2L);
	}
	
	@Test
	@DisplayName("Save category, null name property")
	void saveCategory_ShouldReturn400_NameNull() throws Exception {
		
		// Given
		final URI performedURI = toUri(CATEGORIES);
		
		// When
		final ResultActions perform = mockMvc.perform(post(performedURI)
				                                              .accept(MediaType.APPLICATION_JSON)
				                                              .contentType(MediaType.APPLICATION_JSON)
				                                              .content(TestHelper.toJson(getNewCategoryNoName())));
		
		// Then
		perform.andExpect(status().isBadRequest())
		       .andExpect(jsonPath("$.status", is(BAD_REQUEST.name())))
		       .andExpect(jsonPath("$.timestamp", notNullValue()))
		       .andExpect(jsonPath("$.message", notNullValue(String.class)))
		       .andExpect(jsonPath("$.details").isArray())
		       .andExpect(jsonPath("$.details", hasSize(1)));
		
		Assertions.assertThat(Long.valueOf(categoryRepository.count())).isEqualTo(2L);
	}
	
	@Test
	@DisplayName("Save category, multiple constraints violation")
	void saveCategory_ShouldReturn400_MultipleFieldsError() throws Exception {
		
		// Given
		final URI performedURI = toUri(CATEGORIES);
		
		// When
		final ResultActions perform = mockMvc.perform(post(performedURI)
				                                              .accept(MediaType.APPLICATION_JSON)
				                                              .contentType(MediaType.APPLICATION_JSON)
				                                              .content(TestHelper.toJson(getExistingCategoryNoName())));
		
		// Then
		perform.andExpect(status().isBadRequest())
		       .andExpect(jsonPath("$.status", is(BAD_REQUEST.name())))
		       .andExpect(jsonPath("$.timestamp", notNullValue()))
		       .andExpect(jsonPath("$.message", notNullValue(String.class)))
		       .andExpect(jsonPath("$.details").isArray())
		       .andExpect(jsonPath("$.details", hasSize(2)));
		
		Assertions.assertThat(Long.valueOf(categoryRepository.count())).isEqualTo(2L);
	}
	
	@Test
	@DisplayName("Update category, successful")
	@Transactional
	void updateCategory_ShouldReturn200() throws Exception {
		
		// Given
		final ApiCategory modifiedCategory = ApiCategoryCatalog.getExistingCategoryValid();
		final URI performedURI = toUri(CATEGORIES, CATEGORY_ID, modifiedCategory.getId());
		
		// When
		final ResultActions perform = mockMvc.perform(put(performedURI)
				                                              .accept(MediaType.APPLICATION_JSON)
				                                              .contentType(MediaType.APPLICATION_JSON)
				                                              .content(TestHelper.toJson(modifiedCategory)));
		
		// Then
		perform.andExpect(status().isOk())
		       .andExpect(jsonPath("$.id", is(modifiedCategory.getId().intValue())))
		       .andExpect(jsonPath("$.name", is(modifiedCategory.getName())));
		
		Assertions.assertThat(categoryRepository.getOne(modifiedCategory.getId()))
		          .extracting(Category::getName)
		          .isEqualTo(modifiedCategory.getName());
	}
	
	@Test
	@DisplayName("Update category, category not exist")
	void updateCategory_ShouldReturn404() throws Exception {
		
		// Given
		final ApiCategory category = getUnknownCategory();
		final URI performedURI = toUri(CATEGORIES, CATEGORY_ID, category.getId());
		
		// When
		final ResultActions perform = mockMvc.perform(put(performedURI)
				                                              .accept(MediaType.APPLICATION_JSON)
				                                              .contentType(MediaType.APPLICATION_JSON)
				                                              .content(TestHelper.toJson(category)));
		
		// Then
		perform.andExpect(status().isNotFound())
		       .andExpect(jsonPath("$.status", is(NOT_FOUND.name())))
		       .andExpect(jsonPath("$.timestamp", notNullValue()))
		       .andExpect(jsonPath("$.message", notNullValue(String.class)))
		       .andExpect(jsonPath("$.details").isArray())
		       .andExpect(jsonPath("$.details", hasSize(1)));
	}
	
	@Test
	@DisplayName("Update category, null name property")
	void updateCategory_ShouldReturn400_NoName() throws Exception {
		
		// Given
		final ApiCategory existingCategoryNoName = getExistingCategoryNoName();
		final URI performedURI = toUri(CATEGORIES, CATEGORY_ID, existingCategoryNoName.getId());
		
		// When
		final ResultActions perform = mockMvc.perform(put(performedURI)
				                                              .accept(MediaType.APPLICATION_JSON)
				                                              .contentType(MediaType.APPLICATION_JSON)
				                                              .content(TestHelper.toJson(existingCategoryNoName)));
		
		// Then
		perform.andExpect(status().isBadRequest())
		       .andExpect(jsonPath("$.status", is(BAD_REQUEST.name())))
		       .andExpect(jsonPath("$.timestamp", notNullValue()))
		       .andExpect(jsonPath("$.message", notNullValue(String.class)))
		       .andExpect(jsonPath("$.details").isArray())
		       .andExpect(jsonPath("$.details", hasSize(1)));
	}
	
	@Test
	@DisplayName("Delete category, successful")
	@Transactional
	void deleteCategoryById_Return204() throws Exception {
		
		// Given
		final URI performedURI = toUri(CATEGORIES, CATEGORY_ID, 2L);
		
		// When
		final ResultActions perform = mockMvc.perform(delete(performedURI));
		
		// Then
		perform.andExpect(status().isNoContent());
		
		Assertions.assertThat(Long.valueOf(categoryRepository.count())).isEqualTo(1L);
	}
	
	@Test
	@DisplayName("Delete category, category not exist")
	void deleteCategoryById_Return404() throws Exception {
		
		// Given
		final ApiCategory category = getUnknownCategory();
		final URI performedURI = toUri(CATEGORIES, CATEGORY_ID, category.getId());
		
		// When
		final ResultActions perform = mockMvc.perform(delete(performedURI));
		
		// Then
		perform.andExpect(status().isNotFound())
		       .andExpect(jsonPath("$.status", is(NOT_FOUND.name())))
		       .andExpect(jsonPath("$.timestamp", notNullValue()))
		       .andExpect(jsonPath("$.message", notNullValue(String.class)))
		       .andExpect(jsonPath("$.details").isArray())
		       .andExpect(jsonPath("$.details", hasSize(1)));
		
		Assertions.assertThat(Long.valueOf(categoryRepository.count())).isEqualTo(2L);
	}
	
	@Test
	@DisplayName("Delete category, category in use")
	void deleteCategoryById_Return409() throws Exception {
		
		// Given
		final Long expectedId = 1L;
		final URI performedURI = toUri(CATEGORIES, CATEGORY_ID, expectedId);
		
		// When
		final ResultActions perform = mockMvc.perform(delete(performedURI));
		
		// Then
		perform.andExpect(status().isConflict())
		       .andExpect(jsonPath("$.status", is(CONFLICT.name())))
		       .andExpect(jsonPath("$.timestamp", notNullValue()))
		       .andExpect(jsonPath("$.message", notNullValue(String.class)))
		       .andExpect(jsonPath("$.details").isArray())
		       .andExpect(jsonPath("$.details", hasSize(1)));
		
		Assertions.assertThat(Long.valueOf(categoryRepository.count())).isEqualTo(2L);
	}
}
