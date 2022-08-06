package com.pjt.coupang.user.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
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
    @CreationTimestamp
    Timestamp createdAt;

    public User() {
    }

    public static UserBuilder builder(String email, String password) {
        if (email == null || password == null) {
            throw new IllegalArgumentException("필수 항목 누락");
        }
        return new UserBuilder().email(email);
    }

}
