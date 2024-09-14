package com.example.Project3_v1.controller;

import com.example.Project3_v1.RequestDto.UserUpgradeRequest;
import com.example.Project3_v1.entity.Admin;
import com.example.Project3_v1.entity.User;
import com.example.Project3_v1.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/create")
    public boolean createAdmin () {
        return adminService.createAdmin();
    }

    @GetMapping("/view")
    public List<Admin> getAllAdmins() {
        return adminService.getAdmin();
    }


@GetMapping("/check-user-upgrade-request")
public User getUserApplyUpgradeRequests(
        @RequestParam UserUpgradeRequest upgradeRequest) {
            return adminService.getUserApplyUpgradeRequests(upgradeRequest);
}


}
