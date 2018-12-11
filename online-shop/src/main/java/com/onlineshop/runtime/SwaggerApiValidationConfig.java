package com.onlineshop.runtime;

import java.io.IOException;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.atlassian.oai.validator.springmvc.OpenApiValidationFilter;
import com.atlassian.oai.validator.springmvc.OpenApiValidationInterceptor;
import com.atlassian.oai.validator.springmvc.SpringMVCLevelResolverFactory;

@Configuration
public class SwaggerApiValidationConfig implements WebMvcConfigurer {

	private final OpenApiValidationInterceptor validationInterceptor;

	/**
	 * @param apiSpecification
	 *            the {@link Resource} to your OpenAPI / Swagger schema
	 */
	@Autowired
	public SwaggerApiValidationConfig(@Value("classpath:swagger-api.json") final Resource apiSpecification)
			throws IOException {
		final OpenApiInteractionValidator validator = OpenApiInteractionValidator
				.createFor(apiSpecification.getURL().getPath())
				.withLevelResolver(SpringMVCLevelResolverFactory.create()).withBasePathOverride("/v2").build();
		this.validationInterceptor = new OpenApiValidationInterceptor(validator);
	}

	@Bean
	public Filter validationFilter() {
		return new OpenApiValidationFilter(true, true);
	}

	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		registry.addInterceptor(validationInterceptor);
	}

}