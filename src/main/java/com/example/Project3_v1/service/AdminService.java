package com.example.Project3_v1.service;

import com.example.Project3_v1.dto.user.UserUpgradeRequest;
import com.example.Project3_v1.entity.Admin;
import com.example.Project3_v1.entity.User;
import com.example.Project3_v1.repository.AdminRepository;
import com.example.Project3_v1.repository.StoreRepository;
import com.example.Project3_v1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired UserRepository userRepository;
    @Autowired AdminRepository adminRepository;
    @Autowired UserService userService;
    @Autowired StoreRepository storeRepository;
    @Autowired StoreService storeService;

    //Create a new admin account
    public boolean createAdmin() {
        adminRepository.deleteAll();

        Admin admin1 = new Admin();
        admin1.setUsername("admin1");
        admin1.setPassword("password1");
        admin1.setAuthorities("ROLE_ADMIN,READ,WRITE,CONFIRM");
        adminRepository.save(admin1);

        Admin admin2 = new Admin();
        admin2.setUsername("admin2");
        admin2.setPassword("password2");
        admin2.setAuthorities("ROLE_ADMIN,READ,WRITE,CONFIRM");
        adminRepository.save(admin2);

        return true;
    }

    public String classificationList (String classification) {
        return "ok";
    }

    public List<Admin> getAdmin () { return adminRepository.findAll(); }


    //check the list of business user conversion applications
    public User getUserApplyUpgradeRequests(UserUpgradeRequest upgradeRequest) {
        return userRepository.findByUpgradeReason(upgradeRequest.getUpgradeReason())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    //accept business user conversion applications



    // or reject business user conversion applications

    // check the list of shopping malls that have been applied for opening

    //approve or disapprove after confirming the information,
    // reason must be written together

    //accept the request for closure of the shopping mall after confirming it.

    //



}
