package com.example.springsecurity3_1.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

public class TokenFilter extends AbstractAuthenticationProcessingFilter {
     public TokenFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
//        String authorization = request.getHeader("AUTHORIZATION");
//        String bearer = authorization.substring(authorization.indexOf("Bearer ") + 1);

        String username = request.getHeader("PROPERTY_NO") + ":" + request.getHeader("BSNS_CODE");
        String password = request.getHeader("API-KEY");

        UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username,
                password);

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        return this.getAuthenticationManager().authenticate(authentication);
    }

    /**
     if (this.postOnly && !request.getMethod().equals("POST")) {
        throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
     }
     String username = obtainUsername(request);
     username = (username != null) ? username.trim() : "";
     String password = obtainPassword(request);
     password = (password != null) ? password : "";
     UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username,
     password);
     // Allow subclasses to set the "details" property
     setDetails(request, authRequest);
     return this.getAuthenticationManager().authenticate(authRequest);
     */


}

//public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {
//
//    AuthenticationFilter(final RequestMatcher requiresAuth) {
//        super(requiresAuth);
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
//
//        Optional tokenParam = Optional.ofNullable(httpServletRequest.getHeader(AUTHORIZATION)); //Authorization: Bearer TOKEN
//        String token= httpServletRequest.getHeader(AUTHORIZATION);
//        token= StringUtils.removeStart(token, "Bearer").trim();
//        Authentication requestAuthentication = new UsernamePasswordAuthenticationToken(token, token);
//        return getAuthenticationManager().authenticate(requestAuthentication);
//
//    }
//
//    @Override
//    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
//        SecurityContextHolder.getContext().setAuthentication(authResult);
//        chain.doFilter(request, response);
//    }
//}