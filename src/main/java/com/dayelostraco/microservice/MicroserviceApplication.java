package com.dayelostraco.microservice;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.List;

import static com.google.common.base.Predicates.or;
import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

@SpringBootApplication
@ComponentScan(basePackages = {"com.dayelostraco.*"})
@EnableJpaRepositories(basePackages = {"com.dayelostraco.repository"})
@EnableCaching
@EnableSwagger2
public class MicroserviceApplication {

    /**
     * Kicks off the Spring Boot application
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceApplication.class, args);
    }
    private final TypeResolver typeResolver;

    @Autowired
    public MicroserviceApplication(TypeResolver typeResolver) {
        this.typeResolver = typeResolver;
    }

    /**
     * Configures the Docket that will be used to generate the Swagger JSON from the
     * Spring Context.
     *
     * @return Docket
     */
    @Bean
    public Docket microserviceApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(
                        new ApiInfo(
                                "SpringBoot µservice SeedAPI",
                                "SpringBoot µservice API Description",
                                "1.0",
                                "http://www.termsofserviceurl.com",
                                new Contact("Dayel Ostraco", "http://meetdayel.today", "dayel.ostraco@gmail.com"),
                                "The MIT License",
                                "https://opensource.org/licenses/MIT")
                )
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(paths()) // Use PathSelectors.any() to expose all Spring endpoints
                .build()
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(
                        newRule(typeResolver.resolve(DeferredResult.class,
                                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)))
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,
                        newArrayList(new ResponseMessageBuilder()
                                .code(500)
                                .message("500 message")
                                .responseModel(new ModelRef("Error"))
                                .build()))
                .enableUrlTemplating(true)
                .securitySchemes(newArrayList(apiKey()))
                .securityContexts(newArrayList(securityContext()))
                ;
    }

    /**
     * Configure the API Key and how it will be transmitted to the API used by the generated
     * Swagger documentation.
     *
     * @return ApiKey
     */
    private ApiKey apiKey() {
        return new ApiKey("apiKey", "apiKey", "header");
    }

    /**
     * Configure the Security Context to be leveraged for the Swagger documentation.
     *
     * @return SecurityContext
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/anyPath.*"))
                .build();
    }

    /**
     * Configure the AuthorizationScopes to be used with the Swagger documentation.
     *
     * @return List<SecurityReference>
     */
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(
                new SecurityReference("mykey", authorizationScopes));
    }

    /**
     * Set OAuth or API Key security settings
     *
     * @return SecurityConfiguration
     */
    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration(
                "client-id",
                "realm",
                "app-name",
                "microservice",
                "APIKEY",
                ApiKeyVehicle.HEADER,
                "apiKey",
                "");
    }

    /**
     * Configure the Swagger UI
     *
     * @return UiConfiguration
     */
    @Bean
    UiConfiguration uiConfig() {
        return new UiConfiguration(
                "validatorUrl");
    }

    /**
     * Set the paths that will be exposed to the generated Swagger JSON.
     *
     * @return Predicate containing all paths to be exposed.
     */
    private Predicate<String> paths() {
        return or(
                regex("/sample.*"),
                regex("/api.*")
        );
    }
}
