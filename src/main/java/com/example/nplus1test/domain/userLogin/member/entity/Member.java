package com.example.nplus1test.domain.userLogin.member.entity;

import com.example.nplus1test.domain.userLogin.common.status.Gender;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "member",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_member_login_id", columnNames = {"loginId"})
        }
)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 30, updatable = false)
    private String loginId;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE) // 날짜만 입력되게 설정
    private LocalDate birthDate;

    @Column(nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false, length = 30)
    private String email;

    // --- 생성자 ---
    protected Member() {
        // JPA는 기본 생성자가 반드시 필요 (protected 권장)
    }

    public Member(String loginId, String password, String name,
                  LocalDate birthDate, Gender gender, String email) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.email = email;
    }

    public Member(Object loginId, String loginId1, String password, String name, LocalDate birthDate, Gender gender, String email) {
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

    // --- Getter ---
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    // --- Setter (ID는 보통 안 만듦, 필요 시 추가 가능) ---
    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MemberRole> memberRole = new ArrayList<>();

    // 양방향 편의 메서드 (권장)
    public void addMemberRole(MemberRole mr) {
        memberRole.add(mr);
        mr.setMember(this);
    }

    public void removeMemberRole(MemberRole mr) {
        memberRole.remove(mr);
        mr.setMember(null);
    }

    public List<MemberRole> getMemberRole() { return memberRole; }
    public void setMemberRole(List<MemberRole> memberRole) { this.memberRole = memberRole; }
}
