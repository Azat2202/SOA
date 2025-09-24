package ru.itmo.soa.configurations;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.openapitools.jackson.nullable.JsonNullable;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    final Converter<String, JsonNullable<String>> stringJsonNullableConverter = new AbstractConverter<>() {
        @Override
        protected JsonNullable<String> convert(final String source) {
            if (source == null) {
                return null;
            }
            return JsonNullable.of(source);
        }
    };

    final Converter<Integer, JsonNullable<Integer>> integerJsonNullableConverter = new AbstractConverter<>() {
        @Override
        protected JsonNullable<Integer> convert(final Integer source) {
            if (source == null) {
                return null;
            }
            return JsonNullable.of(source);
        }
    };



    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.addConverter(stringJsonNullableConverter);
        mapper.addConverter(integerJsonNullableConverter);
        return mapper;
    }
} 