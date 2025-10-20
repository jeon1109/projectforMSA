package com.example.nplus1test.domain.userLogin.Users.repository;

import com.example.nplus1test.domain.userLogin.Users.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface jpaUserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);
    boolean existsByEmail(String email);
}
