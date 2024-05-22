package com.example.DenisProj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

        String port = System.getenv("PORT");
        if (port == null || port.isEmpty()) {
            // Porta predefinita, ad esempio 8080
            port = "8080";
        }

        System.setProperty("server.port", port);

        SpringApplication.run(DemoApplication.class, args);
    }
}
