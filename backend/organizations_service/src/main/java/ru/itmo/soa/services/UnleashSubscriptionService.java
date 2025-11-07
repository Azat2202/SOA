package ru.itmo.soa.services;

import io.getunleash.Unleash;
import io.getunleash.event.UnleashEvent;
import io.getunleash.event.UnleashSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.itmo.soa.datasource_configuration.ClientDatabase;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnleashSubscriptionService implements UnleashSubscriber {

    private final Unleash unleash;
    private final DatabaseService databaseService;
    
    @Value("${unleash.database.feature-flag:use-postgresql}")
    private String databaseFeatureFlag;

    @Override
    public void on(UnleashEvent event) {
        log.info("Unleash toggles updated, updating database type");
        // При обновлении фичей обновляем тип БД
        updateDatabaseType();
    }

    /**
     * Обновляет тип используемой БД на основе значения фичафлага
     * Если фичафлаг включен, используется MySQL, иначе PostgreSQL
     */
    private void updateDatabaseType() {
        try {
            boolean useMySQL = unleash.isEnabled(databaseFeatureFlag);
            ClientDatabase newDatabaseType = useMySQL ? ClientDatabase.MYSQL : ClientDatabase.POSTGRESQL;
            databaseService.setConfiguration(newDatabaseType);
        } catch (Exception e) {
            log.error("Error updating database type based on feature flag '{}'", databaseFeatureFlag, e);
        }
    }
}

