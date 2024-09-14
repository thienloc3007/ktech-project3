package com.example.Project3_v1.controller;

import com.example.Project3_v1.RequestDto.StoreCreateRequest;
import com.example.Project3_v1.RequestDto.StoreDeleteRequest;
import com.example.Project3_v1.RequestDto.StoreUpdateRequest;
import com.example.Project3_v1.entity.Store;
import com.example.Project3_v1.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
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
            @RequestBody
            StoreCreateRequest request) {
        return storeService.createStore(request);
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
