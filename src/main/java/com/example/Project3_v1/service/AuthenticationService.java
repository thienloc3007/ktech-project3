package com.example.Project3_v1.service;

import com.example.Project3_v1.dto.AuthenticationRequest;
import com.example.Project3_v1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticationService  {
    private final UserRepository userRepository;

    public boolean authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        //đoạn code bên trên tự chế, ko biết đúng hay ko nữa

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        System.out.println(request.getPassword());
        System.out.println(user.getPassword());

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        System.out.println(hashedPassword);
        String password = user.getPassword();
        String userPasswordEncoded = passwordEncoder.encode(password);
        System.out.println(userPasswordEncoded);

        return passwordEncoder.matches(request.getPassword(), userPasswordEncoded);

//    return (Objects.equals(request.getPassword(), user.getPassword()));
    }
}

