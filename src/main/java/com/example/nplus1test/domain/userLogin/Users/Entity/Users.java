package com.example.nplus1test.domain.userLogin.Users.Entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private String id;

    @Column(nullable = false , name = "password")
    private String password;

    @Column(nullable = false , name = "email")
    private String email;

}
