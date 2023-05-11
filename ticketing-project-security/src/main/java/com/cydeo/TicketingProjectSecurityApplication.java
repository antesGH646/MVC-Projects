package com.cydeo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TicketingProjectSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketingProjectSecurityApplication.class, args);
    }
	@Bean
	public ModelMapper mapper(){
		return new ModelMapper();
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

}
