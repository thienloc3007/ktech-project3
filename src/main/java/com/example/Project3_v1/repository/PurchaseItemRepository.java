package com.example.Project3_v1.repository;

import com.example.Project3_v1.entity.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseItemRepository extends JpaRepository <PurchaseItem, Integer> {
}

