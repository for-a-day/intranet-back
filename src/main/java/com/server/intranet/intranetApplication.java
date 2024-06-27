package com.server.intranet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class intranetApplication {

    public static void main(String args[]){
        SpringApplication.run(intranetApplication.class, args);
    }
}
