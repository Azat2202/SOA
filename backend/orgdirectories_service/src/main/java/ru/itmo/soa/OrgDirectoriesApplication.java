package ru.itmo.soa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "ru.itmo.soa.external")
public class OrgDirectoriesApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrgDirectoriesApplication.class, args);
    }
}

