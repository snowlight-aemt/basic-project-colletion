package com.example.springsecurity3_1.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TokenFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;
//    private final AuthenticationEntryPoint authenticationEntryPoint;

    public TokenFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
//        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            UsernamePasswordAuthenticationToken authRequest = convert(request);
            if (request.getHeader("COMPANY_ID") == null) {
                this.logger.trace("Did not process authentication request since failed to find "
                        + "username and password in Basic Authorization header");
                filterChain.doFilter(request, response);
                return;
            }
            if (authenticationIsRequired(authRequest.getName())) {
                Authentication authResult = this.authenticationManager.authenticate(authRequest);
                SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
                context.setAuthentication(authResult);
                this.securityContextHolderStrategy.setContext(context);
                //        if (this.logger.isDebugEnabled()) {
                //            this.logger.debug(LogMessage.format("Set SecurityContextHolder to %s", authResult));
                //        }
                // ????
                this.securityContextRepository.saveContext(context, request, response);
            }
        } catch (AuthenticationException ex) {
            this.securityContextHolderStrategy.clearContext();
//            this.logger.debug("Failed to process authentication request", ex);
//            this.rememberMeServices.loginFail(request, response);
//            onUnsuccessfulAuthentication(request, response, ex);
//            if (this.ignoreFailure) {
            filterChain.doFilter(request, response);
//            }
//            else {
//                this.authenticationEntryPoint.commence(request, response, ex);
//            }
            return;
        }

        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken convert(HttpServletRequest request) {
        String companyId = request.getHeader("COMPANY_ID");
//            String bsnsCode = request.getHeader("BSNS_CODE");
//            String propertyNo = request.getHeader("PROPERTY_NO");
//            String vendorId = request.getHeader("PROPERTY_NO");
        String apiKey = request.getHeader("API_KEY");
        return UsernamePasswordAuthenticationToken
//                    .unauthenticated(companyId + ":" + bsnsCode + ":" + propertyNo + ":" + vendorId, apiKey);
                .unauthenticated(companyId, apiKey);
    }

    protected boolean authenticationIsRequired(String username) {
        Authentication existingAuth = this.securityContextHolderStrategy.getContext().getAuthentication();
        if (existingAuth == null || !existingAuth.getName().equals(username) || !existingAuth.isAuthenticated()) {
            return true;
        }
        return (existingAuth instanceof AnonymousAuthenticationToken);
    }

    SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();
}