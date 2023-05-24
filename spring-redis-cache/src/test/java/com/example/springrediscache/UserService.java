package com.example.springrediscache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users create(Users user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "myCache", key = "#id")
    public Users getUser(Long id) {
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
