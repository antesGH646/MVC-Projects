package com.cydeo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

//    /**
//     * The first login is comes from spring-boot not from your application
//     * This method overrides the Spring boot authentication.
//     * Behind the scene the user entry user details are encoded.
//     * This method do not validate with the database.
//     * Instead, it validates with what is in the memory
//     * @param passwordEncoder PasswordEncoder
//     * @return encoded password
//     */
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//        //manually creating a list of user details and adding users
//        List<UserDetails> userDetailsList = new ArrayList<>();
//        userDetailsList.add(
//                new User("mike", passwordEncoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))));
//                new User("mike", passwordEncoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER")));
//        return new InMemoryUserDetailsManager(userDetailsList);//b/c validates with in memory not from db
//    }

    /**
     * This method modifies the security by specifying what types of roles can
     * access what type of pages.
     * It may filter to permit all roles to certain pages
     * but authenticates or limits the access of the other pages.
     * The authentication might be basic or other types.
     * Note that: hasAuthority() has a prefix ROLE_ by default.
     * If you permit the login page to all roles, the spring boot login will no more pop up.
     * Not that: the antMatchers() is used to filter authorization based on end points
     * @param httpSecurity HttpSecurity
     * @return httpSecurity
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeRequests()
//                .antMatchers("/user/**").hasRole("Admin")
                .antMatchers("/user/**").hasAuthority("Admin")
                .antMatchers("/project/**").hasAuthority("Manager")
                .antMatchers("/task/employee/**").hasAuthority("Employee")
                .antMatchers("/task/**").hasAuthority("Manager")
//                .antMatchers("/task/**").hasAnyRole("EMPLOYEE","ADMIN")
//                .antMatchers("task/**").hasAuthority("ROLE_EMPLOYEE")

                .antMatchers(
                        "/",
                        "/login",
                        "/fragments/**",
                        "/assets/**",
                        "/images/**"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
              //  .httpBasic()
                .formLogin().loginPage("/login")
                .defaultSuccessUrl("/welcome")
                .failureUrl("/login?error=true")
                .permitAll()
                .and().build();
    }
}
