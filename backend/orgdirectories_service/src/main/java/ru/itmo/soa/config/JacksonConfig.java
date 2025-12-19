package ru.itmo.soa.config;

import com.fasterxml.jackson.databind.Module;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public Module jsonNullableModule() {
        // Registers support for JsonNullable<>, required for openapi-generated models
        return new JsonNullableModule();
    }
}

