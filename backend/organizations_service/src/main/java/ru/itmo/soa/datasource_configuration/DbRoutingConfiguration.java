package ru.itmo.soa.datasource_configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.metamodel.Metamodel;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.PostgreSQLDialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DbRoutingConfiguration {

    @Value("${datasource.postgresql.url}")
    private String postgresqlUrl;

    @Value("${datasource.postgresql.username}")
    private String postgresqlUsername;

    @Value("${datasource.postgresql.password}")
    private String postgresqlPassword;

    @Value("${datasource.postgresql.driver-class-name}")
    private String postgresqlDriverClassName;

    @Value("${datasource.mysql.url}")
    private String mysqlUrl;

    @Value("${datasource.mysql.username}")
    private String mysqlUsername;

    @Value("${datasource.mysql.password}")
    private String mysqlPassword;

    @Value("${datasource.mysql.driver-class-name}")
    private String mysqlDriverClassName;

    @Bean
    public DataSource mysqlDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(mysqlDriverClassName);
        config.setJdbcUrl(mysqlUrl);
        config.setUsername(mysqlUsername);
        config.setPassword(mysqlPassword);
        config.setMaximumPoolSize(10);
        return new HikariDataSource(config);
    }

    @Bean
    public DataSource postgresqlDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(postgresqlDriverClassName);
        config.setJdbcUrl(postgresqlUrl);
        config.setUsername(postgresqlUsername);
        config.setPassword(postgresqlPassword);
        config.setMaximumPoolSize(10);
        return new HikariDataSource(config);
    }

    @Bean
    @Primary
    public DataSource routingDataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(ClientDatabase.MYSQL, mysqlDataSource());
        targetDataSources.put(ClientDatabase.POSTGRESQL, postgresqlDataSource());

        ClientDataSourceRouter router = new ClientDataSourceRouter();
        router.setTargetDataSources(targetDataSources);
        router.setDefaultTargetDataSource(mysqlDataSource());
        router.afterPropertiesSet();
        return router;
    }
}