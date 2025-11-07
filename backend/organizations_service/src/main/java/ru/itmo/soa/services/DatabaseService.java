package ru.itmo.soa.services;

import org.springframework.stereotype.Service;
import ru.itmo.soa.OrganizationsApplication;
import ru.itmo.soa.datasource_configuration.ClientDatabase;
import ru.itmo.soa.datasource_configuration.ClientDatabaseContextHolder;

@Service
public class DatabaseService {

    public ClientDatabase getConfiguration(){
        return ClientDatabaseContextHolder.getClientDatabase();
    }

    public ClientDatabase setConfiguration(ClientDatabase clientDatabase){
        ClientDatabaseContextHolder.set(clientDatabase);
        OrganizationsApplication.restart();
        return ClientDatabaseContextHolder.getClientDatabase();
    }
}
