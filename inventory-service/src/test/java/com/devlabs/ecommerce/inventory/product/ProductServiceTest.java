package com.devlabs.ecommerce.inventory.product;

import com.devlabs.ecommerce.inventory.exception.ResourceNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@Mock
	private ProductRepository productRepository;
	
	@Test
	public void findById_thenSuccess(){
		
		// Given
		final ProductService productService = new ProductService(productRepository);
		Mockito.when(productRepository.findById(ProductCatalog.getProductWithId().getId()))
		       .thenReturn(Optional.of(ProductCatalog.getProductWithId()));
		
		// When
		final Product actualProduct = productService.findById(ProductCatalog.getProductWithId().getId());
		
		// Then
		Assertions.assertThat(actualProduct).isEqualTo(ProductCatalog.getProductWithId());
	}
	
	@Test
	public void findById_ThrowResourceNotFoundException(){
		
		// Given
		final ProductService productService = new ProductService(productRepository);
		Mockito.when(productRepository.findById(ProductCatalog.getProductWithId().getId()))
		       .thenReturn(Optional.empty());
		
		// When
		final Throwable throwable = Assertions.catchThrowable(
				() -> productService.findById(ProductCatalog.getProductWithId().getId()));
		
		// Then
		Assertions.assertThat(throwable).isInstanceOf(ResourceNotFoundException.class);
	}
	
}