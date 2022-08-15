package com.pjt.globalmarket.address.dao;

import com.pjt.globalmarket.address.domain.Address;
import com.pjt.globalmarket.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAddressesByUser(User user);

    Optional<Address> findAddressByUserAndMain(User user, Boolean main);
}
