package com.example.Project3_v1.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    @Getter
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String name;
    private Integer age;
    private String email;
    private String phoneNumber;
    private String profilePicture;
    private String upgradeReason;
    // ROLE_USER,ROLE_ADMIN,READ,WRITE,CREATE,COMMENT
    // ROLE_USER = (Inactive, General, Business, Administrator)
    private String authorities;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
//        // {"ROLE_USER","ROLE_ADMIN","READ","WRITE","CREATE","COMMENT"}
//        for (String authority: authorities.split(",")) {
//            authorityList.add(new SimpleGrantedAuthority(authority));
//        }
//        return authorityList;
        return Arrays.stream(authorities.split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public static CustomUserDetails fromEntity(User user) {
        CustomUserDetails details = new CustomUserDetails();
        details.id = user.getId();
        details.username = user.getUsername();
        details.password = user.getPassword();
        details.email = user.getEmail();
        details.phoneNumber = user.getPhoneNumber();
        details.authorities = user.getAuthorities();
        return details;
    }
}