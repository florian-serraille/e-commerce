package com.devlabs.ecommerce.inventory;

import com.devlabs.ecommerce.inventory.product.Product;
import com.devlabs.ecommerce.inventory.product.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootApplication
public class InventoryServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	
	@Bean
	@Profile("dev")
	CommandLineRunner devStart(final ProductRepository productRepository){
		
		return args -> productRepository.saveAll(Arrays.asList(new Product(null, "first", BigDecimal.ONE),
		                                                       new Product(null, "second", BigDecimal.TEN)));
	}
	
}
