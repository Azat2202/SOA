package ru.itmo.soa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.itmo.soa.datasource_configuration.ClientDatabase;
import ru.itmo.soa.datasource_configuration.ClientDatabaseContextHolder;

@SpringBootApplication
@EnableTransactionManagement
public class OrganizationsApplication {
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        OrganizationsApplication.class.getClassLoader().defin
        context = SpringApplication.run(OrganizationsApplication.class, args);
    }

    public static void restart() {
        Thread thread = new Thread(() -> {
            ConfigurableApplicationContext ctx = context;
//            ctx.close();
            ctx.refresh();
//            context = SpringApplication.run(OrganizationsApplication.class);
        });

        thread.setDaemon(false);
        thread.start();
    }

}