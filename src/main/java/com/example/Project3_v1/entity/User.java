package com.example.Project3_v1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String name;
    private Integer age;
    private String email;
    private String phoneNumber;
    private String profilePicture;
    private String userType; //(Inactive, General, Business, Administrator)

    @OneToMany(mappedBy = "owner")
    private List<Store> store;
}
