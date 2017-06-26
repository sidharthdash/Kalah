package com.example.sidharth.khala.repo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by Sidharth on 6/25/17.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage("com.example.sidharth.khala"))
                .paths(regex("/play.*"))
                .build()
                .apiInfo(metaData());

    }

    private ApiInfo metaData() {
        return new ApiInfo(
                "Welcome to the game of Kalah",
                "Spring Boot REST API for Kalah Game.  The player having most stones " +
                        "in its khala is the winner. " +
                        "In case of a draw both the players are winner",
                "1.0",
                "Terms of service",
                new Contact("Sidharth Dash", "https://www.linkedin.com/in/sidharth-dash", "sidharthdash19@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0");
    }
}