package com.example.springsecurity_2_7.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(Ordered.LOWEST_PRECEDENCE - 100)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain httpSecurityBuilder(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                    .mvcMatchers("/test", "/test/1").permitAll()
                    .mvcMatchers("/admin").hasRole("ADMIN")
                    .mvcMatchers("/user").hasRole("USER")
                    .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic().and()
                .build();
    }
    @Bean
    public InMemoryUserDetailsManager securityFilterChain() {
        UserDetails user = User.withUsername("user01").password("{noop}1234").roles("USER").build();
        return new InMemoryUserDetailsManager(user);
    }
}
