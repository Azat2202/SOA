package ru.itmo.soa.datasource_configuration;

import org.springframework.util.Assert;
import ru.itmo.soa.OrganizationsApplication;

public class ClientDatabaseContextHolder {

    private static ClientDatabase CONTEXT = ClientDatabase.POSTGRESQL;

    public static void set(ClientDatabase clientDatabase) {
        Assert.notNull(clientDatabase, "clientDatabase cannot be null");
        CONTEXT = clientDatabase;
        OrganizationsApplication.restart();
    }

    public static ClientDatabase getClientDatabase() {
        return CONTEXT;
    }

}
