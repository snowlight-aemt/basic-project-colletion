package com.example.springrediscache;

import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository extends JpaRepository<Users, Long> {
}
