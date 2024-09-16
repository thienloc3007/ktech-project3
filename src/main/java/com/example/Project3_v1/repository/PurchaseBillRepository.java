package com.example.Project3_v1.repository;

import com.example.Project3_v1.entity.PurchaseBill;
import com.example.Project3_v1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseBillRepository extends JpaRepository<PurchaseBill, Integer> {


}
