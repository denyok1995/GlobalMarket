package com.pjt.globalmarket.user.dao;

import com.pjt.globalmarket.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByEmailAndProviderAndDeletedAt(String email, String provider, ZonedDateTime deletedAt);

    Optional<User> findUserByEmailAndPasswordAndProviderAndDeletedAt(String email, String password, String provider, ZonedDateTime deletedAt);
}
