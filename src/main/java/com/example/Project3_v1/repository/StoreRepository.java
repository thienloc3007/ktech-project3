package com.example.Project3_v1.repository;

import com.example.Project3_v1.entity.Store;
import com.example.Project3_v1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {
    boolean existsByStoreName(String storeName);

    @Query("SELECT store FROM Store store WHERE store.storeStatus like '%REQUEST TO OPEN%' ")
    List<Store> findStoresOpenRequest();

    @Query("SELECT store FROM Store store WHERE store.storeStatus like '%REQUEST TO CLOSE%' ")
    List<Store> findStoresDeleteRequest();
    
}
