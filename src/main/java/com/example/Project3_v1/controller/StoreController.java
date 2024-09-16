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
    @PutMapping("/update")
    Store updateStore (
            @RequestBody StoreUpdateRequest updatedStore) {
        // Lấy thông tin user từ token, truyền vào userDetails
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        // Lấy thông tin store từ userDetails
        Store store =  userDetails.getStore();

        return storeService.updateStore(store.getId(), updatedStore);
    }

    //Delete
    @DeleteMapping("/delete-request")
    String deleteStore (
            @RequestBody StoreDeleteRequest reason) {
        // Lấy thông tin user từ token, truyền vào userDetails
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        // Lấy thông tin store từ userDetails
        Store store =  userDetails.getStore();
        storeService.deleteStore(store.getId(), reason);
        return "Delete Request was submitted";
    }

    @GetMapping("/search")
    List<Store> searchStore(
            @RequestParam("name") String keyword,
            @RequestParam("classification") Integer classification) {
        return storeService.searchStore(keyword, classification);
    }
}
