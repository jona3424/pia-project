package com.example.back.service;

import com.example.back.entities.Users;
import com.example.back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<Users> findAll() {
        return userRepository.findAll();
    }

    public Optional<Users> findById(Integer id) {
        return userRepository.findById(id);
    }

    public Users save(Users user) {
        return userRepository.save(user);
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
}

