package com.example.Project3_v1.service;

import com.example.Project3_v1.RequestDto.UserCreationRequest;
import com.example.Project3_v1.RequestDto.UserUpdateRequest;
import com.example.Project3_v1.RequestDto.UserUpgradeRequest;
import com.example.Project3_v1.entity.Store;
import com.example.Project3_v1.entity.User;
import com.example.Project3_v1.repository.StoreRepository;
import com.example.Project3_v1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Validated
public class UserService {
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
        user.setAuthorities("ROLE_INACTIIVEUSER,READ");


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

    public void deleteUser (Integer id) {
        userRepository.deleteById(id);
    }



}

