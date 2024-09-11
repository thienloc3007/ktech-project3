package com.example.Project3_v1.controller;

import com.example.Project3_v1.RequestDto.UserCreationRequest;
import com.example.Project3_v1.RequestDto.UserUpdateRequest;
import com.example.Project3_v1.entity.User;
import com.example.Project3_v1.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    User createUser(
            @RequestBody
            @Valid
            UserCreationRequest request) {
        return userService.createUser(request);
    }

    @GetMapping
    List<User> getUsers() {
        return  userService.getUsers();
    }

    @GetMapping("/{id}")
    User getUser (
            @PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    User updateUser (
            @PathVariable Integer id,
            @RequestBody @Valid UserUpdateRequest updatedUser) {
        return userService.updateUser(id, updatedUser);
    }

    @DeleteMapping("/{id}")
    void deleteUser (
            @PathVariable Integer id) {
        userService.deleteUser(id);
    }
}
