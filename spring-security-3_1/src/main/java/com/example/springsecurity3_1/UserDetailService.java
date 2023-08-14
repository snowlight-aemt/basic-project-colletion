package com.example.springsecurity3_1;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public String login(String username, String password) {
        Customer customer = customerRepository.login(username, password).orElseThrow(IllegalArgumentException::new);
        String token = UUID.randomUUID().toString();
        customer.setToken(token);

        return token;
    }

    public User getUserByToken(String token) {
        Customer customer = customerRepository.findByToken(token).orElseThrow(IllegalAccessError::new);
        return new User(customer.getUsername(), customer.getPassword());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username).get();
        return new User(customer.getUsername(), customer.getPassword());
    }
}
