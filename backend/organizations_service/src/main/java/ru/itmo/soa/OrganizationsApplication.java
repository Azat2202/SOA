package ru.itmo.soa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class OrganizationsApplication {
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(OrganizationsApplication.class, args);
    }

    public static synchronized void restart() {
        Thread thread = new Thread(() -> {
            System.out.println("Restarting Spring context...");
            ConfigurableApplicationContext ctx = context;
            if (ctx != null) {
                SpringApplication.exit(ctx, () -> 0);
            }
            context = SpringApplication.run(OrganizationsApplication.class);
        });

        thread.setDaemon(false);
        thread.start();
    }

}