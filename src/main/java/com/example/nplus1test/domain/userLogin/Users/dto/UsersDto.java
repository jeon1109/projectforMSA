package com.example.nplus1test.domain.userLogin.Users.dto;

import com.example.nplus1test.domain.paymentService.Entity.Payment;
import com.example.nplus1test.domain.userLogin.Users.Entity.Users;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class UsersDto {
    private String id;
    private String password;
    private String email;

}
