package com.example.Project3_v1.RequestDto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreationRequest {
    private String username;

    @Size(min = 4, message = "Password must be at least 4 characters")
    private String password;
    private String nickname;
    private String name;
    private Integer age;
    private String email;
    private String phoneNumber;
    private String profilePicture;
    private String userType;
}
