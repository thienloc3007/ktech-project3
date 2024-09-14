package com.example.Project3_v1.service;

import com.example.Project3_v1.dto.user.UserCreationRequest;
import com.example.Project3_v1.dto.user.UserUpdateRequest;
import com.example.Project3_v1.dto.user.UserUpgradeRequest;
import com.example.Project3_v1.entity.CustomUserDetails;
import com.example.Project3_v1.entity.User;
import com.example.Project3_v1.repository.StoreRepository;
import com.example.Project3_v1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Validated
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired private StoreRepository storeRepository;

    public User createUser (UserCreationRequest request) {
        User user = new User();
        if (userRepository.existsByUsername(request.getUsername()) ||
                !(request.getPassword().equals(request.getPasswordConfirm())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setAuthorities("ROLE_INACTIVE_USER,READ");


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
        userRepository.save(user);

        if(user.getName() != null && user.getAge() != null
                && user.getEmail()!= null
                && user.getPhoneNumber()!= null)
        user.setAuthorities("ROLE_GENERAL_USER,READ,WRITE");

        return userRepository.save(user);
    }

    public User upgradeUser (Integer id, UserUpgradeRequest upgradeRequest) {
        User user = getUserById(id);
        user.setUpgradeReason(upgradeRequest.getUpgradeReason());
//        userRepository.save(user);

//        Store personalStore = new Store();
//        personalStore.setStoreStatus("Preparing");
//        storeRepository.save(personalStore);
//
//        if(user.getUpgradeReason()!= null)
//            user.setStore(personalStore);

        return userRepository.save(user);
    }

    public List<User> getUserApplyUpgradeRequests () {
        return userRepository.findGeneralUsersWithUpgradeRequests();
    }

    public User acceptUserUpgradeRequest (Integer id) {
        // 1) Lấy danh sách các user đang gửi request:
        List<User> requestUsers = userRepository.findGeneralUsersWithUpgradeRequests();
        // 2) Kiểm tra user có nằm trong danh sách requestUsers hay không:
        for (User requestUser : requestUsers) {
            // if (user in requestUsers)
            if(Objects.equals(requestUser.getId(), id)) {
                // 3a) Nếu có, thì cập nhật ROLE, lưu và trả về
                requestUser.setAuthorities("ROLE_BUSINESS_USER,READ,WRITE,SELL");
                return userRepository.save(requestUser);
            }
        }
        // 3b) Nếu không, thì trả về null
        return null;
    }

    public User declineUserUpgradeRequest (Integer id) {
        List<User> requestUsers = userRepository.findGeneralUsersWithUpgradeRequests();
        for (User requestUser : requestUsers) {
            if(Objects.equals(requestUser.getId(), id)) {
                requestUser.setUpgradeReason(null);
                return userRepository.save(requestUser);
            }
        }
        return null;
    }

    public void deleteUser (Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty())
            throw new UsernameNotFoundException(username);
//        return User
//                .withUsername(username)
//                .password(optionalUser.get().getPassword())
//                .build();
        return CustomUserDetails.fromEntity(optionalUser.get());
    }
}

