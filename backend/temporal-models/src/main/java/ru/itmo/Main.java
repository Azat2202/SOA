package ru.itmo;

import ru.itmo.temporal_models.User;

public class Main {
    public static void main(String[] args) {
        User user = User.newBuilder()
                .setId("1")
                .setName("John")
                .build();
        System.out.println("Hello, World!");
    }
}