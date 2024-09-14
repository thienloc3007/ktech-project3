package com.example.Project3_v1.dto.user;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserCreationRequest {
    private String username;

    @Size(min = 4, message = "Password must be at least 4 characters")
    private String password;
    private String passwordConfirm;
    private String authorities;
}
