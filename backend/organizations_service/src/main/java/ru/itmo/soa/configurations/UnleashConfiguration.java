package ru.itmo.soa.configurations;

import io.getunleash.DefaultUnleash;
import io.getunleash.Unleash;
import io.getunleash.util.UnleashConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UnleashConfiguration {

    @Value("${unleash.api.url}")
    String unleashApiUrl;

    @Value("${unleash.api.key}")
    String unleashApiKey;

    @Bean
    public Unleash unleash() {
        UnleashConfig config = UnleashConfig.builder()
                .appName("organizations")
                .instanceId("instance-1")
                .unleashAPI(unleashApiUrl)
                .apiKey(unleashApiKey)
                .synchronousFetchOnInitialisation(true)
                .build();

        return new DefaultUnleash(config);
    }
}
