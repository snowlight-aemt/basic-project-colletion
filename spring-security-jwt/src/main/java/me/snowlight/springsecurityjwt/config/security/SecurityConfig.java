package me.snowlight.springsecurityjwt.config.security;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import me.snowlight.springsecurityjwt.config.security.filter.JwtAuthenticationFilter;
import me.snowlight.springsecurityjwt.config.security.filter.JwtAuthenticationProvider;
import me.snowlight.springsecurityjwt.config.security.filter.JwtProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
//    @Bean
//    public AuthenticationProvider authenticationProvider(JwtProcessor jwtProcessor) {
//        return new JwtAuthenticationProvider(jwtProcessor);
//    }
    private final UnauthorizedAuthenticationEntryPoint unauthorizedAuthenticationEntryPoint;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
//new JwtAuthenticationProvider(jwtProcessor)

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtProcessor jwtProcessor) throws Exception {
        http.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers(new AntPathRequestMatcher("/hello")).permitAll()
//                            .requestMatchers(new AntPathRequestMatcher("/test")).rol
//                            .requestMatchers(new AntPathRequestMatcher("/test")).permitAll()
                        .anyRequest().authenticated();
                })
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(unauthorizedAuthenticationEntryPoint))
                .authenticationProvider(jwtAuthenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter(http), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private Filter jwtAuthenticationFilter(HttpSecurity http) {
        return new JwtAuthenticationFilter(http, "/test/filter");
    }

}
