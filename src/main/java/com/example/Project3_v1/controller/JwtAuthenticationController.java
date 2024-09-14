package com.example.Project3_v1.controller;

import com.example.Project3_v1.dto.jwt.JwtRequestDto;
import com.example.Project3_v1.dto.jwt.JwtResponseDto;
import com.example.Project3_v1.dto.user.UserCreationRequest;
import com.example.Project3_v1.entity.User;
import com.example.Project3_v1.jwt.JwtTokenUtils;
import com.example.Project3_v1.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/tokenAuth")
public class JwtAuthenticationController {
    private final JwtTokenUtils tokenUtils;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    public JwtAuthenticationController(
            JwtTokenUtils tokenUtils,
            UserDetailsService userDetailsServiceService,
            UserService userService,
            PasswordEncoder passwordEncoder
    ) {
        this.tokenUtils = tokenUtils;
        this.userDetailsService = userDetailsServiceService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // Send POST request to /token/issue
    // including username,password
    // to get a JwtToken
    @PostMapping("/login")
    public JwtResponseDto issueJwt(@RequestBody JwtRequestDto request) {
        // 아이디로 사용자 조회
        // 1) Try to find user by username
        UserDetails userDetails;
        System.out.println(request.getUsername());
        System.out.println(request.getPassword());
        System.out.println(userDetailsService.loadUserByUsername(request.getUsername()));
        try {
            userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        } catch (UsernameNotFoundException ignored) {
        // 1a) If not found, return HttpStatus.NOT_FOUND
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username not found");
        }
        System.out.println(userDetails.getUsername());
        // 비밀번호 일치 확인
        // 1b) If found userDetails, then
        // 2) Compare the passwords
        // 2a) If request.getPassword() not equal to userDetails.getPassword(), return HttpStatus.UNAUTHORIZED (403 ERROR)
        if (!(Objects.equals(request.getPassword(), userDetails.getPassword()))) //to compare two plaintext password
        // if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) //to compare two a plaintext password with a hashed password
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password");

        // 2b) Else, request.getPassword() equal to userDetails.getPassword()
        // then, generate token for the found userDetails and return a string of token.
        // JWT 발급
        String jwt = tokenUtils.generateToken(userDetails);
        JwtResponseDto response = new JwtResponseDto();
        response.setToken(jwt);
        return response;
    }

    @PostMapping("/signup")
    User createUser(@RequestBody @Valid UserCreationRequest request) {
        return userService.createUser(request);
    }

}