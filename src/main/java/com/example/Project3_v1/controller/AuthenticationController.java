package com.example.Project3_v1.controller;

import com.example.Project3_v1.dto.AuthenticationRequest;
import com.example.Project3_v1.dto.AuthenticationResponse;
import com.example.Project3_v1.dto.user.UserCreationRequest;
import com.example.Project3_v1.entity.User;
import com.example.Project3_v1.service.AuthenticationService;
import com.example.Project3_v1.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private AuthenticationService authenticationService;
    private UserService userService;


    @PostMapping("/login")
    AuthenticationResponse authenticate(
            @RequestBody
            AuthenticationRequest request) {
        boolean result = authenticationService.authenticate(request);
        return AuthenticationResponse.builder()
                .authenticated(result)
                .build();
    }

    @PostMapping("/signup")
    User createUser(
            @RequestBody
            @Valid
            UserCreationRequest request) {
        return userService.createUser(request);
    }
}