package com.example.controller;

import com.example.back.entities.Users;
import com.example.back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Users> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Integer id) {
        Optional<Users> Users = userService.findById(id);
        return Users.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Users createUser(@RequestBody Users Users) {
        return userService.save(Users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Integer id, @RequestBody Users userDetails) {
        Optional<Users> Users = userService.findById(id);
        if (Users.isPresent()) {
            Users updatedUser = Users.get();
            updatedUser.setUsername(userDetails.getUsername());
            updatedUser.setPassword(userDetails.getPassword());
            updatedUser.setSecurityQuestion(userDetails.getSecurityQuestion());
            updatedUser.setSecurityAnswer(userDetails.getSecurityAnswer());
            updatedUser.setFirstName(userDetails.getFirstName());
            updatedUser.setLastName(userDetails.getLastName());
            updatedUser.setGender(userDetails.getGender());
            updatedUser.setAddress(userDetails.getAddress());
            updatedUser.setPhoneNumber(userDetails.getPhoneNumber());
            updatedUser.setEmail(userDetails.getEmail());
            updatedUser.setProfilePicture(userDetails.getProfilePicture());
            updatedUser.setCreditCardNumber(userDetails.getCreditCardNumber());
            updatedUser.setRole(userDetails.getRole());
            updatedUser.setIsActive(userDetails.getIsActive());
            return ResponseEntity.ok(userService.save(updatedUser));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

