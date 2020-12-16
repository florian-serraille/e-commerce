package com.devlabs.ecommerce.inventory.product;

import com.devlabs.ecommerce.inventory.exception.ResourceNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
class ProductServiceTest {

	@Mock
	private ProductRepository productRepository;

	@Autowired
	private ProductMapper productMapper;

	@Test
	public void findById_thenSuccess(){

		// Given
		final ProductService productService = new ProductService(productRepository, productMapper);
		final Product product = ProductCatalog.getProductWithId();

		Mockito.when(productRepository.findById(product.getId()))
		       .thenReturn(Optional.of(product));

		// When
		final ApiProduct actualProduct = productService.findById(product.getId());

		// Then
		Assertions.assertThat(actualProduct)
		          .extracting(ApiProduct::getId, ApiProduct::getName, ApiProduct::getPrice)
		          .contains(product.getId(), product.getName(), product.getPrice());
	}

	@Test
	public void findById_ThrowResourceNotFoundException(){

		// Given
		final ProductService productService = new ProductService(productRepository, productMapper);
		Mockito.when(productRepository.findById(ProductCatalog.getProductWithId().getId()))
		       .thenReturn(Optional.empty());

		// When
		final Throwable throwable = Assertions.catchThrowable(
				() -> productService.findById(ProductCatalog.getProductWithId().getId()));

		// Then
		Assertions.assertThat(throwable).isInstanceOf(ResourceNotFoundException.class);
	}

}