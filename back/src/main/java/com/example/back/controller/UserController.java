package com.example.back.controller;

import com.example.back.dto.ChangePasswordRequest;
import com.example.back.dto.UsersDTO;
import com.example.back.dto.VerifySecurityAnswerRequest;
import com.example.back.entities.Users;
import com.example.back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
    public ResponseEntity<UsersDTO> getUserById(@PathVariable Integer id) {
        UsersDTO usersDTO = userService.findById(id);
        return ResponseEntity.ok(usersDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@RequestBody UsersDTO userDto) {
        Users savedUser = userService.save(userDto,"gost");
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Users> updateUser(@RequestBody UsersDTO user,@PathVariable Integer id) {
        Users updatedUser = userService.update(user,id);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Users> login(@RequestBody Users user) {
        Users foundUser = userService.login(user.getUsername(), user.getPassword());
        return ResponseEntity.ok(foundUser);

    }

    @GetMapping("/login-requests")
    public ResponseEntity<List<Users>> getLoginRequests() {
        List<Users> loginRequests = userService.findLoginRquests();
        if(loginRequests.isEmpty()) {
            return ResponseEntity.ok().body(Collections.emptyList());
        }
        return ResponseEntity.ok(loginRequests);
    }

    @PostMapping("/approve-login-request/{id}")
    public ResponseEntity<Void> approveLoginRequest(@PathVariable Integer id) {
        userService.approveLoginRequest(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reject-login-request/{id}")
    public ResponseEntity<Void> rejectLoginRequest(@PathVariable Integer id) {
        userService.rejectLoginRequest(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/with-old-password")
    public ResponseEntity<?> changePasswordWithOldPassword(@RequestBody ChangePasswordRequest request) {
        boolean success = userService.changePasswordWithOldPassword(request.getOldPassword(), request.getNewPassword(), request.getUsername());
        if (success) {
            return ResponseEntity.ok().body("Password changed successfully");
        } else {
            return ResponseEntity.status(400).body("Old password is incorrect or new password format is invalid");
        }
    }

    @GetMapping("/security-question/{username}")
    public ResponseEntity<?> getSecurityQuestion(@PathVariable String username) {
        String securityQuestion = userService.getSecurityQuestion(username);
        if (securityQuestion != null) {
            return ResponseEntity.ok().body(securityQuestion);
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    @PostMapping("/verify-security-answer")
    public ResponseEntity<?> verifySecurityAnswer(@RequestBody VerifySecurityAnswerRequest request) {
        boolean success = userService.verifySecurityAnswer(request.getUsername(), request.getSecurityAnswer());
        if (success) {
            return ResponseEntity.ok().body("Security answer is correct");
        } else {
            return ResponseEntity.status(400).body("Security answer is incorrect");
        }
    }

        @PostMapping("/with-security-answer")
    public ResponseEntity<?> changePasswordWithSecurityAnswer(@RequestBody ChangePasswordRequest request) {
        boolean success = userService.changePasswordWithSecurityAnswer(request.getUsername(), request.getNewPassword());
        if (success) {
            return ResponseEntity.ok().body("Password changed successfully");
        } else {
            return ResponseEntity.status(400).body("New password format is invalid");
        }
    }

    @GetMapping("/existing-users/{role}")
    public ResponseEntity<List<Users>> getUsersByRole(@PathVariable String role) {
        List<Users> users = userService.findByRole(role);
        if (users.isEmpty()) {
            return ResponseEntity.ok().body(Collections.emptyList());
        }
        return ResponseEntity.ok(users);
    }

    @PostMapping("/block-user/{id}")
    public ResponseEntity<?> blockUser(@PathVariable Integer id) {
        boolean b = userService.blockUser(id);
        if(b){
            return ResponseEntity.ok().body("User blocked successfully");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }
    }

    @GetMapping("/number-of-guests")
    public ResponseEntity<?> getNumberOfGuests() {
        int numberOfUsers = userService.getNumberOfGuests();
        return ResponseEntity.ok().body(numberOfUsers);
    }
    @PostMapping("/create-waiter")
    public ResponseEntity<Users> createWaiter(@RequestBody UsersDTO userDto) {
        Users savedUser = userService.save(userDto,"konobar");
        return ResponseEntity.ok(savedUser);
    }
}

