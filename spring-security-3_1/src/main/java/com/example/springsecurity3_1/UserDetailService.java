package com.example.springsecurity3_1;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserDetailService {
    private CustomerRepository customerRepository;

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
}
