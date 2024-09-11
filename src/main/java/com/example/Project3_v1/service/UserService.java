package com.example.Project3_v1.service;

import com.example.Project3_v1.RequestDto.UserCreationRequest;
import com.example.Project3_v1.RequestDto.UserUpdateRequest;
import com.example.Project3_v1.entity.User;
import com.example.Project3_v1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser (UserCreationRequest request) {
        User user = new User();
        if (userRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException("Username already exists");

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setNickname(request.getNickname());
        user.setName(request.getName());
        user.setAge(request.getAge());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setProfilePicture(request.getProfilePicture());
        user.setUserType(request.getUserType());

//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
//        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser (Integer id, UserUpdateRequest updateRequest) {
        User user = getUserById(id);
        user.setPassword(updateRequest.getPassword());
        user.setNickname(updateRequest.getNickname());
        user.setName(updateRequest.getName());
        user.setAge(updateRequest.getAge());
        user.setEmail(updateRequest.getEmail());
        user.setPhoneNumber(updateRequest.getPhoneNumber());
        user.setProfilePicture(updateRequest.getProfilePicture());
        user.setUserType(updateRequest.getUserType());

        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
