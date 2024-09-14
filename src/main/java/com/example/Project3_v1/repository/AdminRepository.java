package com.example.Project3_v1.repository;

import com.example.Project3_v1.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    // assuming Admin entity has Integer id field{

}
