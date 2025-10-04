package ru.itmo.soa;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class HelloApplication extends Application {
    public static String EXTERNAL_URL = "http://organizations:8081/organizations";
}