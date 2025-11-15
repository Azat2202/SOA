package ru.itmo.soa.services;

import io.getunleash.Unleash;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.itmo.soa.datasource_configuration.ClientDatabase;
import ru.itmo.soa.datasource_configuration.ClientDatabaseContextHolder;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnleashFeatureFetchService {

    private final Unleash unleash;

    @Value("${unleash.database.feature-flag}")
    private String useMySqlFeatureFlag;

    @Scheduled(fixedRate = 5000, initialDelay = 10000)
    public void fetchFeatures() {
        try {
            boolean useMySql = unleash.isEnabled(useMySqlFeatureFlag);
            ClientDatabase databaseByFeature = useMySql ? ClientDatabase.MYSQL : ClientDatabase.POSTGRESQL;
            log.info("current database status: {}", databaseByFeature.toString());
            if (databaseByFeature != ClientDatabaseContextHolder.getClientDatabase()){
                log.info("changing database...");
                ClientDatabaseContextHolder.set(databaseByFeature);
            }
        } catch (Exception e) {
            log.error("Error fetching feature flag '{}'", useMySqlFeatureFlag, e);
        }
    }
}

