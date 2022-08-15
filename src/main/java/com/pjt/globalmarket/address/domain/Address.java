package com.pjt.globalmarket.address.domain;

import com.pjt.globalmarket.user.domain.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue
    private Long Id;

    private Boolean main;

    private String name;

    private String content;

    @ManyToOne
    private User user;
}
