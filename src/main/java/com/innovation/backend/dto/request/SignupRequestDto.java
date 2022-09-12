package com.innovation.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupRequestDto {
    private String email;
    private String password;
    private String name;
    private String phone_number;
    private String address;
}