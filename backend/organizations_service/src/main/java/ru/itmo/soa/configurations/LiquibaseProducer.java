package ru.itmo.soa.configurations;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Startup
public class LiquibaseProducer {

    private static final Logger log = Logger.getLogger(LiquibaseProducer.class.getName());

    @Resource(lookup = "java:app/jdbc/OrganizationsDS")
    private DataSource dataSource;

    @PostConstruct
    public void runLiquibase() {
        try (Connection connection = dataSource.getConnection()) {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            try (Liquibase liquibase = new Liquibase(
                    "migrations/db-changelog-master.xml",
                    new ClassLoaderResourceAccessor(),
                    database
            )) {
                liquibase.update("");
                log.info("Liquibase migrations completed successfully");
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Liquibase migration failed", e);
            throw new RuntimeException("Liquibase migration failed", e);
        }
    }
}

