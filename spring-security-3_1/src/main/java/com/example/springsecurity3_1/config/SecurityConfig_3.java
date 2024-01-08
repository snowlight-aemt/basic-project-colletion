package com.example.springsecurity3_1.config;

import com.example.springsecurity3_1.filter.ApiKeyAuthenticationEntryPoint;
import com.example.springsecurity3_1.filter.TokenFilter;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig_3 {
    private final ApiKeyAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration) throws Exception {

        http
//                .csrf().disable()
//                .cors().disable()
                .authorizeHttpRequests(request -> request
                	.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .anyRequest().authenticated()	// 어떠한 요청이라도 인증필요
                )
//                .formLogin(login -> login	// form 방식 로그인 사용
//                        .defaultSuccessUrl("/view/dashboard", true)	// 성공 시 dashboard로
//                        .permitAll()	// 대시보드 이동이 막히면 안되므로 얘는 허용
//                )
                .addFilterBefore(new TokenFilter(authenticationConfiguration.getAuthenticationManager()),
                                    UsernamePasswordAuthenticationFilter.class);
//                .logout(withDefaults());	// 로그아웃은 기본설정으로 (/logout으로 인증해제)
                ;
        return http.build();
    }
}
