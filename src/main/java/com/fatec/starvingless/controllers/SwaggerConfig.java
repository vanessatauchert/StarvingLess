package com.fatec.starvingless.controllers;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig  {

    @Bean
    public OpenAPI customApi() {
        final String securitySchemeName = "basicAuth";
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearer-key", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("API Starving Less")
                        .description("Projeto de TCC")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Rafael Medeiros and Vanessa Oliveira")
                                .url("url do front"))


                ).addSecurityItem(new SecurityRequirement()
                        .addList("bearer-jwt", Arrays.asList("reader", "write"))
                        .addList("bearer-key", Collections.emptyList()))
                ;
    }











}
