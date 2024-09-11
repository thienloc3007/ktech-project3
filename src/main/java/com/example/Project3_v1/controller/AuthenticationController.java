package com.example.Project3_v1.controller;

import com.example.Project3_v1.RequestDto.AuthenticationRequest;
import com.example.Project3_v1.Response.AuthenticationResponse;
import com.example.Project3_v1.service.AuthenticationService;
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
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    AuthenticationResponse authenticate(
            @RequestBody
            AuthenticationRequest request) {
        boolean result = authenticationService.authenticate(request);
        return AuthenticationResponse.builder()
                .authenticated(result)
                .build();
    }


}


