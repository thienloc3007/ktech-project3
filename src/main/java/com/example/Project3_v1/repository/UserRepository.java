package com.example.Project3_v1.repository;

import com.example.Project3_v1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);

    boolean existsByUpgradeReason(String upgradeReason);
    Optional <User> findByUpgradeReason(String upgradeReason);

    @Query("SELECT u FROM User u WHERE u.authorities like '%ROLE_GENERAL_USER%' " +
            "AND u.upgradeReason IS NOT NULL")
    List<User> findGeneralUsersWithUpgradeRequests();

    @Query("SELECT u FROM User u WHERE u.authorities like '%ROLE_BUSINESS_USER%' ")
    List<User> findSeller();

}
