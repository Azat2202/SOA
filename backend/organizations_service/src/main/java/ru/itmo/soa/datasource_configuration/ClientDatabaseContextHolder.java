package ru.itmo.soa.datasource_configuration;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.util.Assert;

public class ClientDatabaseContextHolder {

    private static ClientDatabase CONTEXT = ClientDatabase.MYSQL;

    public static void set(ClientDatabase clientDatabase) {
        Assert.notNull(clientDatabase, "clientDatabase cannot be null");
        CONTEXT = clientDatabase;
    }

    public static ClientDatabase getClientDatabase() {
        return CONTEXT;
    }

}
