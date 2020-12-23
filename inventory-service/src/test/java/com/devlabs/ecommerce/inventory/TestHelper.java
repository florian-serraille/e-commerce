/*
 * CABAL BRASIL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * Copyright (c) 2020, Cabal Brasil and/or its affiliates. All rights reserved.
 */
package com.devlabs.ecommerce.inventory;

import com.devlabs.ecommerce.inventory.brand.ApiBrand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.util.UriComponentsBuilder;

import javax.sql.DataSource;
import java.net.URI;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class TestHelper {
	
	@Getter
	private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
	
	public static URI toUri(final String url) {
		return UriComponentsBuilder.fromPath(url).build().toUri();
	}
	
	public static URI toUri(final String url, final String pathVariable, final Long variable) {
		return UriComponentsBuilder.fromPath(url).path(pathVariable).build(variable);
	}
	
	public static String toJson(final Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}
	
	public static void populate(final Resource resource, final DataSource dataSource){
		
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScripts(resource);
		populator.execute(dataSource);
	}
}