package com.fatec.starvingless.controllers;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig  {

    @Bean
    public OpenAPI customApi() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("API Starving Less")
                        .description("Projeto de TCC")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Rafael Medeiros and Vanessa Oliveira")
                                .url("url do front"))
                );
    }









}
