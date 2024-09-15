package com.example.Project3_v1.service;

import com.example.Project3_v1.dto.user.UserUpgradeRequest;
import com.example.Project3_v1.entity.Admin;
import com.example.Project3_v1.entity.Store;
import com.example.Project3_v1.entity.User;
import com.example.Project3_v1.repository.AdminRepository;
import com.example.Project3_v1.repository.StoreRepository;
import com.example.Project3_v1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AdminService {
    @Autowired UserRepository userRepository;
    @Autowired AdminRepository adminRepository;
    @Autowired UserService userService;
    @Autowired StoreRepository storeRepository;
    @Autowired StoreService storeService;

    //Create a new admin account
    public boolean createAdmin() {
        //adminRepository.deleteAll();

        User admin1 = new User();
        admin1.setUsername("admin1");
        admin1.setPassword("password1");
        admin1.setAuthorities("ROLE_ADMIN,READ,WRITE,CONFIRM");
        userRepository.save(admin1);

        User admin2 = new User();
        admin2.setUsername("admin2");
        admin2.setPassword("password2");
        admin2.setAuthorities("ROLE_ADMIN,READ,WRITE,CONFIRM");
        userRepository.save(admin2);

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

    // check the list of shopping malls that have been applied for opening
    public List<Store> getStoreOpenRequest () {
        return storeRepository.findStoresOpenRequest();
    }

    public Store acceptStoresOpenRequest (Integer storeId) {
        // 1) Lấy danh sách các user đang gửi request:
        List<Store> requestStores = storeRepository.findStoresOpenRequest();
        // 2) Kiểm tra user có nằm trong danh sách requestUsers hay không:
        for (Store requestStore : requestStores) {
            // if (user in requestUsers)
            if(Objects.equals(requestStore.getId(), storeId)) {
                // 3a) Nếu có, thì cập nhật ROLE, lưu và trả về
                requestStore.setStoreStatus("OPEN");

                return storeRepository.save(requestStore);
            }
        }
        // 3b) Nếu không, thì trả về null
        return null;
    }

    public Store declineStoreOpenRequest (Integer storeId) {
        List<Store> requestStores = storeRepository.findStoresOpenRequest();
        for (Store requestStore : requestStores) {
            if(Objects.equals(requestStore.getId(), storeId)) {
                requestStore.setStoreStatus("PREPARING");
                return storeRepository.save(requestStore);
            }
        }
        return null;
    }

    //approve or disapprove after confirming the information,
    // reason must be written together

    //accept the request for closure of the shopping mall after confirming it.

    //
    public List<Store> getStoreDeleteRequest () {
        return storeRepository.findStoresDeleteRequest();
    }

    public Store acceptStoresDeleteRequest (Integer storeId) {
        // 1) Lấy danh sách các user đang gửi request:
        List<Store> requestStores = storeRepository.findStoresDeleteRequest();
        // 2) Kiểm tra user có nằm trong danh sách requestUsers hay không:
        for (Store requestStore : requestStores) {
            // if (user in requestUsers)
            if(Objects.equals(requestStore.getId(), storeId)) {
                // 3a) Nếu có, thì cập nhật ROLE, lưu và trả về
                requestStore.setStoreStatus("CLOSED");

                return storeRepository.save(requestStore);
            }
        }
        // 3b) Nếu không, thì trả về null
        return null;
    }


}
