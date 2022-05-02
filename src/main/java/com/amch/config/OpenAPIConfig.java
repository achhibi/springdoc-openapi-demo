package com.amch.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-apis")
                .displayName("public-apis")
                .pathsToMatch("/**")
                .pathsToExclude("/actuator/**")
                .build();
    }

    @Bean
    public GroupedOpenApi actuatorApi() {
        return GroupedOpenApi.builder()
                .group("actuator")
                .pathsToMatch("/actuator/**")
                .build();

    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI().components(new Components())
                .info(new Info().title("Book Application API")
                        .version("1.0")
                        .contact(new Contact().name("Amor CHHIBI").email("email@hotmail.fr"))
                        .description(
                                "This is a sample Spring Boot RESTful service using springdoc-openapi and OpenAPI 3."));
    }

}