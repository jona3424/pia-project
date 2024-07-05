package com.example.back.dto;

import lombok.Data;

@Data
public class VerifySecurityAnswerRequest {
    private String username;
    private String securityAnswer;
}
