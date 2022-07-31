package com.pjt.coupang.user.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
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
