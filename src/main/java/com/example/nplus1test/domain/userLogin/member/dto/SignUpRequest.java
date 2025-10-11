package com.example.nplus1test.domain.userLogin.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SignUpRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    private String address;

    @Pattern(regexp = "\\d{6}-\\d{7}", message = "주민등록번호 형식 오류")
    private String rrn; // 주민등록번호

    // getters / setters (Lombok을 쓸 수도 있음)
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getRrn() { return rrn; }
    public void setRrn(String rrn) { this.rrn = rrn; }
}
