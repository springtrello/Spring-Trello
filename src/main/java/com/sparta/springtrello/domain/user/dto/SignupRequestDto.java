package com.sparta.springtrello.domain.user.dto;

import lombok.Data;

@Data
public class SignupRequestDto {
    private String email;
    private String username;
    private String password;
   // private String profileimage;
    private String role;
}
