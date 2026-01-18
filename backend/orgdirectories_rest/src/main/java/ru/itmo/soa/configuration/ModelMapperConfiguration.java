package ru.itmo.soa.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JsonNullableModule());
        objectMapper.registerModule(new JavaTimeModule());
        
//         Custom module to handle XMLGregorianCalendar -> LocalDate conversion
        SimpleModule flexibleDateModule = new SimpleModule();
        flexibleDateModule.addDeserializer(LocalDate.class, new FlexibleLocalDateDeserializer());
        objectMapper.registerModule(flexibleDateModule);
        
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }

    /**
     * Custom deserializer that handles both LocalDate format (2025-09-24) 
     * and full ISO datetime format (2025-09-24T21:00:00.000+00:00)
     */
    private static class FlexibleLocalDateDeserializer extends JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String dateStr = p.getText();
            if (dateStr == null || dateStr.isEmpty()) {
                return null;
            }
            
            try {
                // Try parsing as LocalDate first (yyyy-MM-dd)
                return LocalDate.parse(dateStr);
            } catch (DateTimeParseException e) {
                // If that fails, try parsing as OffsetDateTime and extract the date
                try {
                    return OffsetDateTime.parse(dateStr).toLocalDate();
                } catch (DateTimeParseException e2) {
                    // As a fallback, just take the first 10 characters (date portion)
                    if (dateStr.length() >= 10) {
                        return LocalDate.parse(dateStr.substring(0, 10));
                    }
                    throw new IOException("Cannot parse date: " + dateStr, e2);
                }
            }
        }
    }
}
