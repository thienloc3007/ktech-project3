package com.example.Project3_v1.dto.user;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    @Size (min = 4)
    private String password;
    private String nickname;
    private String name;
    private Integer age;
    private String email;
    private String phoneNumber;
    private String profilePicture;
    private String bankName;
    private Integer balance;
    private String accountNumber;


}