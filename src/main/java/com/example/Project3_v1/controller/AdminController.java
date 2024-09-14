package com.example.Project3_v1.controller;

import com.example.Project3_v1.RequestDto.UserUpgradeRequest;
import com.example.Project3_v1.entity.Admin;
import com.example.Project3_v1.entity.User;
import com.example.Project3_v1.service.AdminService;
import com.example.Project3_v1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public boolean createAdmin () {
        return adminService.createAdmin();
    }

    @GetMapping("/view")
    public List<Admin> getAllAdmins() {
        return adminService.getAdmin();
    }



    @GetMapping("/check-user-upgrade-request")
    List<User> getUserApplyUpgradeRequests() {
            return userService.getUserApplyUpgradeRequests();
    }

    @PutMapping("/check-user-upgrade-request/{id}/accept")
    public User acceptUserUpgradeRequest (
            @PathVariable Integer id) {
        return userService.acceptUserUpgradeRequest(id);
    }

    @PutMapping("/check-user-upgrade-request/{id}/decline")
    public User declineUserUpgradeRequest (
            @PathVariable Integer id) {
        return userService.declineUserUpgradeRequest(id);
    }

}
