
## Migrating from SpringFox

-   Remove springfox and swagger 2 dependencies. Add  `springdoc-openapi-ui`  dependency instead.
    

```xml
   <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-ui</artifactId>
      <version>1.6.8</version>
   </dependency>
```

-   Replace swagger 2 annotations with swagger 3 annotations (it is already included with  `springdoc-openapi-ui`  dependency). Package for swagger 3 annotations is  `io.swagger.v3.oas.annotations`.
    
    -   `@Api`  →  `@Tag`
        
    -   `@ApiIgnore`  →  `@Parameter(hidden = true)`  or  `@Operation(hidden = true)`  or  `@Hidden`
        
    -   `@ApiImplicitParam`  →  `@Parameter`
        
    -   `@ApiImplicitParams`  →  `@Parameters`
        
    -   `@ApiModel`  →  `@Schema`
        
    -   `@ApiModelProperty(hidden = true)`  →  `@Schema(accessMode = READ_ONLY)`
        
    -   `@ApiModelProperty`  →  `@Schema`
        
    -   `@ApiOperation(value = "foo", notes = "bar")`  →  `@Operation(summary = "foo", description = "bar")`
        
    -   `@ApiParam`  →  `@Parameter`
        
    -   `@ApiResponse(code = 404, message = "foo")`  →  `@ApiResponse(responseCode = "404", description = "foo")`
        
    
-   This step is optional: Only if you have  **multiple**  `Docket`  beans replace them with  `GroupedOpenApi`  beans.
    

Before:

```java
  @Bean
  public Docket publicApi() {
      return new Docket(DocumentationType.SWAGGER_2)
              .select()
              .apis(RequestHandlerSelectors.basePackage("com.amch.public"))
              .paths(PathSelectors.regex("/public.*"))
              .build()
              .groupName("public-apis")
              .apiInfo(apiInfo());
  }

  @Bean
  public Docket adminApi() {
      return new Docket(DocumentationType.SWAGGER_2)
              .select()
              .apis(RequestHandlerSelectors.basePackage("com.amch.public"))
              .paths(PathSelectors.regex("/admin.*"))
              .apis(RequestHandlerSelectors.withMethodAnnotation(Admin.class))
              .build()
              .groupName("admin-apis")
              .apiInfo(apiInfo());
  }
```

Now:

```java
@Bean
public  GroupedOpenApi  publicApi() {
	return  GroupedOpenApi.builder()
		.group("public-apis")
		.displayName("public-apis")
		.pathsToMatch("/**")
		.pathsToExclude("/actuator/**")
		.build();

}  
//You must enable actuator in application.properties
@Bean
public  GroupedOpenApi  actuatorApi() {
	return  GroupedOpenApi.builder()
		.group("actuator")
		.pathsToMatch("/actuator/**")
		.build();
}
```

If you have  **only one**  `Docket` — remove it and instead add properties to your  `application.properties`:

```properties
springdoc.packagesToScan=package1, package2
springdoc.pathsToMatch=/v1, /api/balance/**
```

-   Add bean of  `OpenAPI`  type. See example:
    

```java
 @Bean

public  OpenAPI  springpenAPI() {
	return  new  OpenAPI().components(new  Components())
		.info(new  Info().title("Book Application API")
		.version("1.0")
		.contact(new  Contact().name("Amor CHHIBI").email("email@hotmail.fr"))
		.description("This is a sample Spring Boot RESTful service using springdoc-openapi and OpenAPI3.0"));

}

```

-
