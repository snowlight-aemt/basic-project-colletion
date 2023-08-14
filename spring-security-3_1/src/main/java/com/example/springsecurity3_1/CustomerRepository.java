package com.example.springsecurity3_1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "select u from Customer u")
    Optional<Customer> login(@Param("username") String username,@Param("password") String password);
    Optional<Customer> findByToken(String token);

    Optional<Customer> findByUsername(String username);

}
