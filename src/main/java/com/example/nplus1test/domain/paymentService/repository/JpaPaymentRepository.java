package com.example.nplus1test.domain.paymentService.repository;

import com.example.nplus1test.domain.paymentService.Entity.Payment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaPaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrderId(String orderId);
    Optional<Payment> findByPaymentKeyAndCustomer_Email(String paymentKey, String email);
    Slice<Payment> findAllByCustomer_Email(String email, Pageable pageable);
}
