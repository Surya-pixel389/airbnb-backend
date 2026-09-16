package com.aribnb.backend.repository;

import com.aribnb.backend.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRespository extends JpaRepository<user, Long> {
    Optional<user> findByEmail(String email);
    boolean existsByEmail(String email);
}
