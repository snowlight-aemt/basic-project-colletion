package com.example.springsecurity3_1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
public class Oauth2Config {
    @Value("${spring.security.oauth2.resource-server.opaque-token.introspection-uri}")
    String introspectionUri;

    @Value("${spring.security.oauth2.resource-server.opaque-token.client-id}")
    String clientId;

    @Value("${spring.security.oauth2.resource-server.opaque-token.client-secret}")
    String clientSecret;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.GET, "/message/**").hasAuthority("SCOPE_message:read")
                        .requestMatchers(HttpMethod.POST, "/message/**").hasAuthority("SCOPE_message:write")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer((oauth2) -> oauth2
                        .opaqueToken((opaque) -> opaque
                                .introspectionUri(this.introspectionUri)
                                .introspectionClientCredentials(this.clientId, this.clientSecret)
                        )
                );
        // @formatter:on
        return http.build();
    }
}
