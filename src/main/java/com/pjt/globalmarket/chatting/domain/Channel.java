package com.pjt.globalmarket.chatting.domain;

import com.pjt.globalmarket.user.domain.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Channel {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @OneToOne
    private User opener;

    @ManyToMany
    private List<User> users;

    @CreationTimestamp
    private ZonedDateTime createdAt;
}
