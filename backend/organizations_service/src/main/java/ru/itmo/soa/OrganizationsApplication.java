package ru.itmo.soa;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.devtools.restart.Restarter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class OrganizationsApplication {
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = new SpringApplicationBuilder(OrganizationsApplication.class).run();
    }

    public static synchronized void restart() {
        if (context != null) {
            context.close();
        }
        context = new SpringApplicationBuilder(OrganizationsApplication.class).run();
    }

}