package com.devlabs.ecommerce.configurationservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

@SpringBootTest
class ConfigurationServiceApplicationTests {
	
	@Test
	void contextLoads() {
		
		Throwable thrown = catchThrowable(
				() -> ConfigurationServiceApplication.main(new String[] {}));
		
		assertThat(thrown).isNull();
	}
	
}
