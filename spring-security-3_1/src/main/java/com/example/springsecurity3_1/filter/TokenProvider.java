package com.example.springsecurity3_1.filter;

import com.example.springsecurity3_1.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

//@Component
//@RequiredArgsConstructor
public class TokenProvider { // extends AbstractUserDetailsAuthenticationProvider {
//    private final UserDetailService userDetailService;

//    @Override
//    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//
//    }
//
//    @Override
//    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//        String token = authentication.getCredentials().toString();
//        return userDetailService.getUserByToken(token);
//    }
}


