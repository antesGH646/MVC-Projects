package com.cydeo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BankSimulationSecurityAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankSimulationSecurityAppApplication.class, args);
    }
    /**
     * Since ModelMapper is a 3rd party class that you don't own
     * you cannot use @Component annotation, on the mapper classes
     * so, you have to use the @Bean annotation
     */
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
