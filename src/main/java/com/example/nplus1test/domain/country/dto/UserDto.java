package com.example.nplus1test.domain.country.dto;

import jakarta.validation.constraints.*;
public class UserDto {
    public record SignupRequest(
            @NotBlank @Size(min = 3, max = 50) String username,
            @NotBlank @Size(min = 8, max = 100) String password
    ) {}

    public record LoginRequest(
            @NotBlank String username,
            @NotBlank String password
    ) {}

    public record TokenResponse(String accessToken, String refreshToken) {}

    public record RefreshRequest(@NotBlank String refreshToken) {}
}
