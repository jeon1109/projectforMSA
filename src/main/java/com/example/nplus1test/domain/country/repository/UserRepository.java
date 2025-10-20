package com.example.nplus1test.domain.country.repository;

import com.example.nplus1test.domain.country.entity.UserEntity;
import com.example.nplus1test.domain.userLogin.Users.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    boolean existsByEmail(String username);
}