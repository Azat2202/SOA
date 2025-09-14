package ru.itmo.soa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableTransactionManagement
@EnableMethodSecurity
public class OrganizationsApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrganizationsApplication.class, args);
    }
}