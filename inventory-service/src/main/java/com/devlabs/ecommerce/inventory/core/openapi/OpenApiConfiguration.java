package com.devlabs.ecommerce.inventory.core.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import lombok.AllArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
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
	
	@Bean
	public GroupedOpenApi inventoryOpenApi() {
		
		final String[] paths = { "/api/v1/inventory/**" };
		return GroupedOpenApi.builder().group("inventory").pathsToMatch(paths)
		                     .build();
	}
}
