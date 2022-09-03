package com.pjt.globalmarket.payment.dao;

import com.pjt.globalmarket.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
