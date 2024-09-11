package com.example.Project3_v1.jwt.dto;

import lombok.Data;

@Data
public class JwtRequestDto {
    private String username;
    private String password;
}