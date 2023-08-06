package com.example.springsecurity3_1.config;

import com.example.springsecurity3_1.filter.TokenFilter;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
//    private final AuthenticationProvider provider;

    @Bean
    public SecurityFilterChain webSecurityConfiguration(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration) throws Exception {
        http.cors(CorsConfigurer::disable);
        http.csrf(CsrfConfigurer::disable);

//        new AuthenticationFilter(authenticationManager);
//        http.authenticationProvider(provider);
        TokenFilter filter = new TokenFilter("/hello");

        filter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
        http.addFilterBefore(filter, AnonymousAuthenticationFilter.class);

//        http.securityMatcher("/auth/**").oauth2ResourceServer(oAuth2ResourceServerConfigurer -> {
//            oAuth2ResourceServerConfigurer.jwt(jwtConfigurer ->
//                    jwtConfigurer.decoder(NimbusJwtDecoder.withPublicKey(generate("Public Key")).build())
//            );
//        });
//        http.authorizeHttpRequests(authorizeHttpRequestsCustomizer -> {
//            authorizeHttpRequestsCustomizer.requestMatchers(new AntPathRequestMatcher("/hello/**")).authenticated();
//        });

//        http.formLogin(Customizer.withDefaults());
        http.formLogin(FormLoginConfigurer::disable);
//        http.httpBasic(Customizer.withDefaults());
        http.httpBasic(HttpBasicConfigurer::disable);

        return http.build();
    }

//    @Bean
//    AuthenticationFilter authenticationFilter() throws Exception {
////        new OrRequestMatcher(new AntPathRequestMatcher("/api/**"))
////        final AuthenticationFilter filter = new AuthenticationFilter();
////        filter.setAuthenticationManagerResolver();
//        //filter.setAuthenticationSuccessHandler(successHandler());
//        return filter;
//    }

    public RSAPublicKey generate(final String publicKey) {
        try {
            return(RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.encodeBase64(publicKey.getBytes(), true)));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
