package ru.itmo.soa.services;

import io.getunleash.Unleash;
import io.getunleash.event.ToggleEvaluated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.itmo.soa.datasource_configuration.ClientDatabase;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnleashSubscriptionService {

    private final Unleash unleash;
    private final DatabaseService databaseService;

    @Value("${unleash.database.feature-flag:select-mysql-db}")
    private String databaseFeatureFlag;

    @EventListener
    public void handleUnleashEvent(ToggleEvaluated event) {
        if (databaseFeatureFlag.equals(event.getToggleName())) {
            log.info("Database toggle changed, updating database type");
            updateDatabaseType();
        }
    }

//    @EventListener
//    public void handleUnleashReady(UnleashReadyEvent event) {
//        log.info("Unleash initialized, setting initial database type");
//        updateDatabaseType(event.getUnleash());
//    }

    private void updateDatabaseType() {
        try {
            boolean useMySQL = unleash.isEnabled(databaseFeatureFlag);
            ClientDatabase newDatabaseType = useMySQL ? ClientDatabase.MYSQL : ClientDatabase.POSTGRESQL;
            databaseService.setConfiguration(newDatabaseType);
            log.info("Database type updated to: {}", newDatabaseType);
        } catch (Exception e) {
            log.error("Error updating database type based on feature flag '{}'", databaseFeatureFlag, e);
        }
    }
}

