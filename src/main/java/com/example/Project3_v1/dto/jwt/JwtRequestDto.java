package com.example.Project3_v1.dto.jwt;

import lombok.Data;

@Data
public class JwtRequestDto {
    private String username;
    private String password;
}