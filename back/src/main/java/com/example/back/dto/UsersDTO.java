package com.example.back.dto;
import com.example.back.entities.Users;
import lombok.Data;

import java.util.Base64;
import java.util.Base64.Decoder;

@Data
public class UsersDTO {
    private String username;
    private String password;
    private String securityQuestion;
    private String securityAnswer;
    private String firstName;
    private String lastName;
    private Character gender;
    private String address;
    private String phone;
    private String email;
    private String profilePictureBase64; // Temporary attribute
    private String creditCard;

    // getters and setters

    public byte[] getProfilePicture() {
        if (this.profilePictureBase64 != null) {
            Decoder decoder = Base64.getDecoder();
            return decoder.decode(this.profilePictureBase64);
        }
        return null;
    }

    public UsersDTO() {
    }
    public UsersDTO(Users users) {
        this.username = users.getUsername();
        this.password = users.getPassword();
        this.securityQuestion = users.getSecurityQuestion();
        this.securityAnswer = users.getSecurityAnswer();
        this.firstName = users.getFirstName();
        this.lastName = users.getLastName();
        this.address = users.getAddress();
        this.phone = users.getPhoneNumber();
        this.email = users.getEmail();
        this.creditCard = users.getCreditCardNumber();
        this.profilePictureBase64 = Base64.getEncoder().encodeToString(users.getProfilePicture());
        this.gender = users.getGender();
    }

}
