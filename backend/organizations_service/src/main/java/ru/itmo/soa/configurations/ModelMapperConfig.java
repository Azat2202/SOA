package ru.itmo.soa.configurations;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    final Converter<String, JsonNullable<String>> stringFromJsonNullableConverter = new AbstractConverter<>() {
        @Override
        protected JsonNullable<String> convert(final String source) {
            if (source == null) {
                return null;
            }
            return JsonNullable.of(source);
        }
    };

    final Converter<Integer, JsonNullable<Integer>> integerFromJsonNullableConverter = new AbstractConverter<>() {
        @Override
        protected JsonNullable<Integer> convert(final Integer source) {
            if (source == null) {
                return null;
            }
            return JsonNullable.of(source);
        }
    };

    final Converter<JsonNullable<Integer>, Integer> integerToJsonNullableConverter = new AbstractConverter<>() {
        @Override
        protected Integer convert(final JsonNullable<Integer> source) {
            return source.orElse(null);
        }
    };

    final Converter<JsonNullable<String>, String> stringToJsonNullableConverter = new AbstractConverter<>() {
        @Override
        protected String convert(final JsonNullable<String> source) {
            return source.orElse(null);
        }
    };


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.addConverter(stringFromJsonNullableConverter);
        mapper.addConverter(integerFromJsonNullableConverter);
        mapper.addConverter(integerToJsonNullableConverter);
        mapper.addConverter(stringToJsonNullableConverter);
        return mapper;
    }
} 