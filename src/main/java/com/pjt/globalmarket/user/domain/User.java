package com.pjt.globalmarket.user.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Builder.Default
    private String uuid = String.valueOf(UUID.randomUUID());

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Setter
    private String name;

    @Setter
    private String phone;

    @Builder.Default
    private String role = "ROLE_USER";

    @Builder.Default
    private String provider = "GM";

    @Builder.Default
    private String providerId = String.valueOf(UUID.randomUUID());

    @CreationTimestamp
    private ZonedDateTime createdAt;

    private ZonedDateTime deletedAt;

    public User() {
    }

    public static UserBuilder builder(String email, String password) {
        if (email == null || password == null) {
            throw new IllegalArgumentException("필수 항목 누락");
        }
        return new UserBuilder().email(email).password(password);
    }

}
