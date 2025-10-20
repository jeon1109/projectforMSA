package com.example.nplus1test.domain.userLogin.common.dto;


import com.example.nplus1test.domain.userLogin.common.annotaion.ValidEnum;
import com.example.nplus1test.domain.userLogin.common.status.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MemberDtoRequest {

    private Long id;

    @NotBlank
    @JsonProperty("loginId")
    private String loginId;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\\$%^&*])[a-zA-Z0-9!@#\\$%^&*]{8,20}$",
            message = "영문, 숫자, 특수문자를 포함한 8~20자리로 입력해주세요"
    )
    @JsonProperty("password")
    private String password;

    @NotBlank
    @JsonProperty("name")
    private String name;

    @NotBlank
    @Pattern(
            regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$",
            message = "날짜형식(YYYY-MM-DD)을 확인해주세요"
    )
    @JsonProperty("birthDate")
    private String birthDate; // 문자열로 입력받고 변환

    @NotBlank
    @ValidEnum(enumClass = Gender.class, message = "MAN 이나 WOMAN 중 하나를 선택해주세요")
    @JsonProperty("gender")
    private String gender;

    @NotBlank
    @Email
    @JsonProperty("email")
    private String email;

    // 기본 생성자 (Jackson 직렬화/역직렬화용)
    public MemberDtoRequest() {}

    // 전체 필드 생성자
    public MemberDtoRequest(Long id, String loginId, String password, String name,
                            String birthDate, String gender, String email) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.email = email;
    }

    // Getter
    public Long getId() {
        return id;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    // 문자열 → LocalDate 변환
    public LocalDate getBirthDate() {
        return LocalDate.parse(this.birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    // 문자열 → Gender enum 변환
    public Gender getGender() {
        return Gender.valueOf(this.gender);
    }

    public String getEmail() {
        return email;
    }
}