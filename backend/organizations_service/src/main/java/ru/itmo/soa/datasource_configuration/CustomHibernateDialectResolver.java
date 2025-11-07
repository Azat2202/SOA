package ru.itmo.soa.datasource_configuration;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolver;

public class CustomHibernateDialectResolver implements DialectResolver {
    @Override
    public Dialect resolveDialect(DialectResolutionInfo info) {
        return switch (ClientDatabaseContextHolder.getClientDatabase()) {
            case POSTGRESQL -> new PostgreSQLDialect();
            case MYSQL -> new MySQLDialect();
        };
    }
}
