package com.devlabs.ecommerce.inventory;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

@SpringBootTest(classes = InventoryServiceApplication.class)
class InventoryServiceApplicationTests {
	
	@Test
	void contextLoads() {
		
		Throwable thrown = catchThrowable(
				() -> InventoryServiceApplication.main(new String[] {}));
		
		assertThat(thrown).isNull();
	}
	
}
