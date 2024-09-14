package com.example.Project3_v1.repository;

import com.example.Project3_v1.entity.Classification;
import com.example.Project3_v1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassificationRepository extends JpaRepository<Classification, Integer> {
    boolean existsByName(String name);
    Optional<Classification> findByName(String name);
}
