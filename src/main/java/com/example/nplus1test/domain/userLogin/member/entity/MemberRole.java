package com.example.nplus1test.domain.userLogin.member.entity;

import com.example.nplus1test.domain.userLogin.common.status.Role;
import jakarta.persistence.*;

@Entity
@Table(name = "member_role") // 필요 시 원하는 테이블명으로
public class MemberRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private Role role; // Kotlin의 ROLE -> Java enum Role로 변환했다고 가정

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "member_id",
            foreignKey = @ForeignKey(name = "fk_user_role_member_id")
    )
    private Member member;

    protected MemberRole() {
        // JPA 기본 생성자
    }

    public MemberRole(Role role, Member member) {
        this.role = role;
        this.member = member;
    }

    // getters/setters
    public Long getId() { return id; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
}