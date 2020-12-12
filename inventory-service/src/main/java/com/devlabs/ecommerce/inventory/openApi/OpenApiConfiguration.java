package com.devlabs.ecommerce.inventory.openApi;

import com.devlabs.ecommerce.lib.openApi.ApiDocumentation;
import com.devlabs.ecommerce.lib.openApi.OpenApiDescriptor;
import io.swagger.v3.oas.models.OpenAPI;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@AllArgsConstructor
@Import(ApiDocumentation.class)
public class OpenApiConfiguration {
	
	private final ApiDocumentation doc;
	
	@Bean
	public OpenAPI openAPI() {
		return new OpenApiDescriptor(doc).getOpenAPI();
	}
}
