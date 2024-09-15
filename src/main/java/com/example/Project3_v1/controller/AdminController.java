package com.example.Project3_v1.controller;

import com.example.Project3_v1.entity.Admin;
import com.example.Project3_v1.entity.Store;
import com.example.Project3_v1.entity.User;
import com.example.Project3_v1.service.AdminService;
import com.example.Project3_v1.service.StoreService;
import com.example.Project3_v1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;
    @Autowired
    private StoreService storeService;

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
        User user = userService.acceptUserUpgradeRequest(id);
        Store store = storeService.autocCreateStore(user);
        return user;
    }

    @PutMapping("/check-user-upgrade-request/{id}/decline")
    public User declineUserUpgradeRequest (
            @PathVariable Integer id) {
        return userService.declineUserUpgradeRequest(id);
    }

    @GetMapping("/check-store-open-request")
    List<Store> getStoreOpenRequest () {
        return adminService.getStoreOpenRequest(); //
    }

    @PutMapping("/check-store-open-request/{storeId}/accept")
    public Store acceptStoresOpenRequest (
            @PathVariable Integer storeId) {
        Store store = adminService.acceptStoresOpenRequest(storeId);
        return store;
    }

    @PutMapping("/check-store-open-request/{storeId}/decline")
    public Store declineStoreOpenRequest (
            @PathVariable Integer storeId) {
        return adminService.declineStoreOpenRequest(storeId);
    }

    @GetMapping("/check-store-delete-request")
    List<Store> getStoreDeleteRequest () {
        return adminService.getStoreDeleteRequest(); //
    }

    @PutMapping("/check-store-delete-request/{storeId}/accept")
    public Store acceptStoresDeleteRequest (
            @PathVariable Integer storeId) {
        Store store = adminService.acceptStoresDeleteRequest(storeId);
        return store;
    }

}
