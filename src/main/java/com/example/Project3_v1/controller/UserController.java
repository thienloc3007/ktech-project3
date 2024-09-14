package com.example.Project3_v1.controller;

import com.example.Project3_v1.RequestDto.UserCreationRequest;
import com.example.Project3_v1.RequestDto.UserUpdateRequest;
import com.example.Project3_v1.RequestDto.UserUpgradeRequest;
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

    @PostMapping("/signup")
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

    @PutMapping("/{id}/update")
    User updateUser (
            @PathVariable Integer id,
            @RequestBody @Valid UserUpdateRequest updatedUser) {
        return userService.updateUser(id, updatedUser);
    }

    @PutMapping("/{id}/upgrade")
    User upgradeUser (
            @PathVariable Integer id,
            @RequestBody @Valid UserUpgradeRequest upgradeUser) {
        return userService.upgradeUser(id, upgradeUser)  ;
    }


    @DeleteMapping("/{id}")
    void deleteUser (
            @PathVariable Integer id) {
        userService.deleteUser(id);
    }
}
