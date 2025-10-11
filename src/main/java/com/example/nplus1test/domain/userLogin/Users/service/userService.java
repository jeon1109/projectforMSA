package com.example.nplus1test.domain.userLogin.Users.service;

import com.example.nplus1test.domain.userLogin.Users.Entity.Users;
import com.example.nplus1test.domain.userLogin.Users.repository.jpaUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class userService {
    private final jpaUserRepository jpaUserRepsitory;


    public userService(jpaUserRepository jpaUserRepsitory) {
        this.jpaUserRepsitory = jpaUserRepsitory;
    }

    public Users searchEmail(String email) {
        Users dto =  jpaUserRepsitory.findByEmail(email);

        return dto;
    }

}
