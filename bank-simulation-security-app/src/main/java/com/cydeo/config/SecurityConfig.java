package com.cydeo.config;

import com.cydeo.service.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
    private final SecurityService securityService;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    public SecurityConfig(SecurityService securityService, AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.securityService = securityService;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeRequests()
                .antMatchers("/accounts/**").hasAuthority("Admin")
                .antMatchers("/transaction/**").hasAnyAuthority("Admin","Cashier")
                .antMatchers("/","/login")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
              //  .defaultSuccessUrl("/index")//replaced by implementing success handler below
                .successHandler(authenticationSuccessHandler)
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .and()
                .rememberMe()
                .tokenValiditySeconds(300)
                .key("bankapp")
                .userDetailsService(securityService)
                .and().build();
    }
}
