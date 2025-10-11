package com.example.nplus1test.domain.userLogin.member.dto;
import com.example.nplus1test.domain.userLogin.common.status.Gender;
import com.example.nplus1test.domain.userLogin.member.entity.Member;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class MemberDtoRequest {
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private LocalDate birthDate;
    private Gender gender;
    private String email;

    // 생성자
    public MemberDtoRequest(Long id, String loginId, String password, String name,
                            LocalDate birthDate, Gender gender, String email) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.email = email;
    }

    // equals & hashCode (id 기준으로만 비교하고 싶으면 id만 포함 가능)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MemberDtoRequest)) return false;
        MemberDtoRequest that = (MemberDtoRequest) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Member toEntity() {
        return new Member(
                id,
                loginId,
                password,
                name,
                birthDate,
                gender,
                email
        );
    }
}
