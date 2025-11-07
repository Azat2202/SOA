package ru.itmo.soa.services;

import io.getunleash.DefaultUnleash;
import io.getunleash.Unleash;
import io.getunleash.event.UnleashEvent;
import io.getunleash.event.UnleashSubscriber;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.itmo.soa.datasource_configuration.ClientDatabase;
import ru.itmo.soa.datasource_configuration.ClientDatabaseContextHolder;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnleashSubscriptionService implements UnleashSubscriber {

    private final Unleash unleash;
    private final DatabaseService databaseService;
    
    @Value("${unleash.database.feature-flag:use-postgresql}")
    private String databaseFeatureFlag;

//    @PostConstruct
//    public void init() {
//        // Подписываемся на обновления фичей
//        if (unleash instanceof DefaultUnleash) {
//            ((DefaultUnleash) unleash).addSubscriber(this);
//            log.info("Subscribed to Unleash feature flag updates");
//        } else {
//            log.warn("Unleash instance is not DefaultUnleash, cannot subscribe to updates");
//        }
//
//        // Устанавливаем начальное значение БД на основе текущего состояния фичафлага
//        updateDatabaseType();
//    }

    @Override
    public void on(UnleashEvent event) {
        log.info("Unleash toggles updated, updating database type");
        // При обновлении фичей обновляем тип БД
        updateDatabaseType();
    }

    /**
     * Обновляет тип используемой БД на основе значения фичафлага.
     * Если фичафлаг включен, используется PostgreSQL, иначе MySQL.
     */
    private void updateDatabaseType() {
        try {
            boolean usePostgreSQL = unleash.isEnabled(databaseFeatureFlag);
            ClientDatabase newDatabaseType = usePostgreSQL ? ClientDatabase.POSTGRESQL : ClientDatabase.MYSQL;
            databaseService.setConfiguration(newDatabaseType);
//            ClientDatabase currentDatabaseType = ClientDatabaseContextHolder.getClientDatabase();
//
//            if (currentDatabaseType != newDatabaseType) {
//                log.info("Switching database from {} to {} based on feature flag '{}' (value: {})",
//                        currentDatabaseType, newDatabaseType, databaseFeatureFlag, usePostgreSQL);
//                ClientDatabaseContextHolder.set(newDatabaseType);
//            } else {
//                log.debug("Database type unchanged: {} (feature flag '{}' = {})",
//                        currentDatabaseType, databaseFeatureFlag, usePostgreSQL);
//            }
        } catch (Exception e) {
            log.error("Error updating database type based on feature flag '{}'", databaseFeatureFlag, e);
        }
    }
}

