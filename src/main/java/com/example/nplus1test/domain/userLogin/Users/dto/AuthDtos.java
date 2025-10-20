package com.example.nplus1test.domain.userLogin.Users.dto;

public class AuthDtos {
    public record SignupReq(String email, String password) {

    }
    public record LoginReq(String email, String password) {

    }
    public record TokenRes(String access, String refresh) {

    }
}
