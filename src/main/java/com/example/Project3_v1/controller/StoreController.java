package com.example.Project3_v1.controller;

import com.example.Project3_v1.dto.store.StoreCreateRequest;
import com.example.Project3_v1.dto.store.StoreDeleteRequest;
import com.example.Project3_v1.dto.store.StoreUpdateRequest;
import com.example.Project3_v1.entity.CustomUserDetails;
import com.example.Project3_v1.entity.Store;
import com.example.Project3_v1.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
public class StoreController {
    @Autowired
    private StoreService storeService;

    //Create
    @PostMapping("/signup")
    Store createStore(
            @RequestBody  StoreCreateRequest request) {

        // Lấy thông tin user từ token, truyền vào userDetails
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        // Lấy thông tin store từ userDetails
        Store store =  userDetails.getStore();
        // Truyền store.getId() vào method createStore(storeId, request)

        return storeService.createStore(store.getId(),request);
    }

    //Read all stores
//    @PreAuthorize("hasAuthority('ROLE_GENERAL_USER')")
//    @PreAuthorize("hasAnyAuthority('ROLE_INACTIVE_USER')")
    @GetMapping
    List<Store> getAllStores() {
        return storeService.getAllStores();
    }

    //ReadOne
    @GetMapping("/{id}")
    Store getStoreById (
            @PathVariable Integer id) {
        return storeService.getStoreById(id);
    }

    //Update
    @PutMapping("/{id}/update")
    Store updateStore (
            @PathVariable Integer id,
            @RequestBody StoreUpdateRequest updatedStore) {
        return storeService.updateStore(id, updatedStore);
    }

    //Delete
    @DeleteMapping("/{id}")
    String deleteStore (
            @PathVariable Integer id,
            @RequestBody StoreDeleteRequest reason) {
        storeService.deleteStore(id, reason);
        return "Delete Request was submitted";
    }






}
