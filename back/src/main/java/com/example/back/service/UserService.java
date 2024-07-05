package com.example.back.service;

import com.example.back.dto.UsersDTO;
import com.example.back.entities.Users;
import com.example.back.helpers.AESCrypt;
import com.example.back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<Users> findAll() {
        return userRepository.findAll();
    }

    public UsersDTO findById(Integer id) {
        Optional<Users> byId = userRepository.findById(id);
        if(byId.isPresent()) {
            Users user = byId.get();
            try {
                user.setPassword(AESCrypt.decrypt(user.getPassword()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            UsersDTO userDto = new UsersDTO(user);
            return userDto;
        }
        return null;
    }

    public Users save(UsersDTO userDto, String role) {
        Users user = new Users();
        user.setUsername(userDto.getUsername());

        try{
            user.setPassword(AESCrypt.encrypt(userDto.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        user.setSecurityQuestion(userDto.getSecurityQuestion());
        user.setSecurityAnswer(userDto.getSecurityAnswer());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setGender(userDto.getGender());
        user.setAddress(userDto.getAddress());
        user.setPhoneNumber(userDto.getPhone());
        user.setEmail(userDto.getEmail());
        user.setCreditCardNumber(userDto.getCreditCard());
        user.setRole(role);
        user.setIsActive(false);
        System.out.println(userDto.getProfilePictureBase64());

        byte[] profilePictureBytes;
        if (userDto.getProfilePictureBase64() != null) {
            profilePictureBytes = Base64.getDecoder().decode(userDto.getProfilePictureBase64());
        } else {
            profilePictureBytes = getDefaultProfilePicture();
        }
        user.setProfilePicture(profilePictureBytes);

        return userRepository.saveAndFlush(user);
    }

    private byte[] getDefaultProfilePicture() {
        try {
            File file = ResourceUtils.getFile(  "classpath:images/defaultAvatar.png");
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load default profile picture", e);
        }
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }


    public Users login(String username, String password) {
        try {
            password = AESCrypt.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userRepository.findByUsernameAndPasswordAndIsActive(username, password, true);

    }

    public List<Users> findLoginRquests() {
        return userRepository.findByIsActive(false);
    }
    public void approveLoginRequest(Integer id) {
        Optional<Users> byId = userRepository.findById(id);
        if(byId.isPresent()) {
            Users user = byId.get();
            user.setIsActive(true);
            userRepository.save(user);
        }
    }

    public void rejectLoginRequest(Integer id) {
        Optional<Users> byId = userRepository.findById(id);
        if(byId.isPresent()) {
           byId.get().setIsActive(null);
           userRepository.saveAndFlush(byId.get());
        }
    }

    public boolean changePasswordWithOldPassword(String oldPassword, String newPassword, String username) {

        try {
            oldPassword = AESCrypt.encrypt(oldPassword);
            newPassword = AESCrypt.encrypt(newPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Optional<Users> byUsernameAndPassword = userRepository.findByUsernameAndPassword(username, oldPassword);
        if (byUsernameAndPassword.isPresent()) {
            Users user = byUsernameAndPassword.get();
            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public String getSecurityQuestion(String username) {
        return userRepository.findScurityQuestionByUsername(username);
    }

    public boolean verifySecurityAnswer(String username, String securityAnswer) {
        String correctAnswer = userRepository.findScurityAnswerByUsername(username);
        if (correctAnswer != null) {
            return correctAnswer.equals(securityAnswer);
        }
        return false;
    }

    public boolean changePasswordWithSecurityAnswer(String username, String newPassword) {

        try {
            newPassword = AESCrypt.encrypt(newPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Optional<Users> byUsername = userRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            Users user = byUsername.get();
            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public List<Users> findByRole(String role) {
        return userRepository.findByRoleAndIsActive(role,true);
    }

    public boolean blockUser(Integer id) {
        Optional<Users> byId = userRepository.findById(id);
        if(byId.isPresent()) {
            Users user = byId.get();
            user.setIsActive(false);
            userRepository.save(user);
            return true;
        }
        return false;
    }
    public int getNumberOfGuests() {

        List<Users> gost = userRepository.findByRoleAndIsActive("gost", true);
        return gost.size();
    }
    public Users update(UsersDTO userDto, Integer id) {
        Optional<Users> byId = userRepository.findById(id);
        if(byId.isPresent()) {
            Users user = byId.get();
            user.setUsername(userDto.getUsername());
            try{
                user.setPassword(AESCrypt.encrypt(userDto.getPassword()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            user.setSecurityQuestion(userDto.getSecurityQuestion());
            user.setSecurityAnswer(userDto.getSecurityAnswer());
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setGender(userDto.getGender());
            user.setAddress(userDto.getAddress());
            user.setPhoneNumber(userDto.getPhone());
            user.setEmail(userDto.getEmail());
            user.setCreditCardNumber(userDto.getCreditCard());
            user.setRole("gost");
            user.setIsActive(true);

            byte[] profilePictureBytes;
            if (userDto.getProfilePictureBase64() != null) {
                profilePictureBytes = Base64.getDecoder().decode(userDto.getProfilePictureBase64());
            } else {
                profilePictureBytes = getDefaultProfilePicture();
            }
            user.setProfilePicture(profilePictureBytes);

            return userRepository.save(user);
        }
        return null;
    }

}

