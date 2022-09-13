package com.pjt.globalmarket.user.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

import static com.pjt.globalmarket.user.domain.UserConstant.DEFAULT_PROVIDER;
import static com.pjt.globalmarket.user.domain.UserConstant.ROLE_USER;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "users")
public class User implements Serializable {

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

    // NOTE: role도 grade와 같이 enum을 사용할 수 있지 않나요?
    @Builder.Default
    private String role = ROLE_USER;

    @Builder.Default
    private UserGrade grade = UserGrade.BRONZE;

    @Builder.Default
    private String provider = DEFAULT_PROVIDER;

    @Builder.Default
    private String providerId = String.valueOf(UUID.randomUUID());

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @Setter
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
