package com.cydeo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TicketingOrmProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketingOrmProjectApplication.class, args);
    }

    //a method that returns an object of the mapper
    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }
}
