package ru.itmo.soa.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import ru.itmo.soa.clients.OrgdirectoriesClient;

@Configuration
@RequiredArgsConstructor
public class CountryClientConfig {

    @Value("${orgdirectories_client_url}")
    private String clientUrl;
    private final ObjectMapper objectMapper;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("ru.itmo.soa.gen");
        return marshaller;
    }
    @Bean
    public OrgdirectoriesClient orgdirectoriesClient(Jaxb2Marshaller marshaller) {
        OrgdirectoriesClient client = new OrgdirectoriesClient(objectMapper);
        client.setDefaultUri(clientUrl);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}