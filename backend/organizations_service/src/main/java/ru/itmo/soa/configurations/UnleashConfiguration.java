package ru.itmo.soa.configurations;

import io.getunleash.DefaultUnleash;
import io.getunleash.Unleash;
import io.getunleash.event.ClientFeaturesResponse;
import io.getunleash.event.UnleashSubscriber;
import io.getunleash.util.UnleashConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
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
                .fetchTogglesInterval(5)
                .build();

        return new DefaultUnleash(config);
    }
}
