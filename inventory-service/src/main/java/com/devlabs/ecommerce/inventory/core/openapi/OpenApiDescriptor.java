package com.devlabs.ecommerce.inventory.core.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.Getter;

public class OpenApiDescriptor {
	
	@Getter
	private final OpenAPI openAPI;
	
	public OpenApiDescriptor(final ApiDocumentation doc) {
		
		this.openAPI = new OpenAPI()
				               .components(new Components())
				               .info(new Info().title(doc.getName())
				                               .version(doc.getVersion())
				                               .contact(doc.buildContact())
				                               .description(doc.getDescription())
				                               .license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}
}
