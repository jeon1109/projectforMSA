package com.example.nplus1test.domain.userLogin.Users.service;

import com.example.nplus1test.domain.userLogin.Users.Entity.Users;
import com.example.nplus1test.domain.userLogin.Users.repository.jpaUserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class userService {
    private final jpaUserRepository jpaUserRepsitory;

    public userService(jpaUserRepository jpaUserRepsitory) {
        this.jpaUserRepsitory = jpaUserRepsitory;
    }

    public Users searchEmail(String email) {
        Users dto = jpaUserRepsitory.findByEmail(email);
        return dto;
    }

}
