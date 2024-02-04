package com.example.hotel.repository;

import com.example.hotel.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hotel.model.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    UserEntity findByRole(Role role);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
