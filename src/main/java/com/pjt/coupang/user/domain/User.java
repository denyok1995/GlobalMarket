package com.pjt.coupang.user.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String name;
    private String salt;
    private String phone;

    public User(String email, String password, String name, String phone, String salt) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.salt = salt;
    }

}
