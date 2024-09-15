package com.example.Project3_v1.service;

import com.example.Project3_v1.dto.store.StoreCreateRequest;
import com.example.Project3_v1.dto.store.StoreDeleteRequest;
import com.example.Project3_v1.dto.store.StoreUpdateRequest;
import com.example.Project3_v1.entity.Classification;
import com.example.Project3_v1.entity.Store;
import com.example.Project3_v1.entity.User;
import com.example.Project3_v1.repository.ClassificationRepository;
import com.example.Project3_v1.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StoreService {
    @Autowired private StoreRepository storeRepository;
    @Autowired private ClassificationRepository classificationRepository;

    //Create
    public Store autocCreateStore(User owner) {
        Store store = new Store();
        store.setStoreStatus("PREPARING");
        store.setOwner(owner);
        return  storeRepository.save(store);
    }

    //confirm the reason for disapproval

    //Update
    public Store createStore(Integer storeId, StoreCreateRequest createStoreRequest) {
        Store store = getStoreById(storeId);
        if (storeRepository.existsByStoreName(createStoreRequest.getStoreName())) {
            throw new RuntimeException("This store name cannot be used");}
        store.setStoreName(createStoreRequest.getStoreName());
        store.setIntroduction(createStoreRequest.getIntroduction());
        Classification classification = classificationRepository.findById(createStoreRequest.getClassificationId()).orElseThrow();
        store.setClassification(classification);
        store.setStoreStatus("REQUEST TO OPEN");

        return storeRepository.save(store);
    }



    //Read
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    //ReadOne
    public Store getStoreById(Integer id) {
        return storeRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Store not found"));
        }

    //Update
    public Store updateStore(Integer id, StoreUpdateRequest updateStoreRequest) {
        Store store = getStoreById(id);
        if (storeRepository.existsByStoreName(updateStoreRequest.getStoreName())) {
            throw new RuntimeException("This store name cannot be used");}
        store.setStoreName(updateStoreRequest.getStoreName());
        store.setIntroduction(updateStoreRequest.getIntroduction());

        Classification classification = classificationRepository.findById(updateStoreRequest.getClassificationId()).orElseThrow();

        store.setClassification(classification);
        return storeRepository.save(store);
    }

    //Delete
    public void deleteStore(Integer id, StoreDeleteRequest deleteStoreRequest) {
        //looking for open store basing on its id before deleting it
        Store store = getStoreById(id);

        //checking if store is open before closing it
        if (store.getStoreStatus().equals("OPEN"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        //setting delete reason for closed store
        store.setDeleteReason(deleteStoreRequest.getReason());

        //add deleteReason to store
        storeRepository.save(store);

//        store.setStoreStatus("CLOSED");
//        storeRepository.deleteById(id);
    }



}
