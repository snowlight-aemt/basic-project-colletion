package com.example.springsecurity3_1.config;

import com.example.springsecurity3_1.filter.ApiKeyAuthenticationEntryPoint;
import com.example.springsecurity3_1.filter.TokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig_2 {
    private final ApiKeyAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration) throws Exception {
        http.csrf(CsrfConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .formLogin(FormLoginConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable);
//                    .httpBasic(Customizer.withDefaults());
//            .httpBasic(HttpBasicConfigurer::disable);

        http.exceptionHandling(v -> {
            v.authenticationEntryPoint(authenticationEntryPoint);
        });

        http.authorizeHttpRequests(customizer ->
                        customizer.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                                .anyRequest().authenticated()
        );
        http.addFilterBefore(new TokenFilter(authenticationConfiguration.getAuthenticationManager()), BasicAuthenticationFilter.class);

        return http.build();
    }
}
