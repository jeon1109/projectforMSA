package com.example.nplus1test.domain.paymentService.service;

import com.example.nplus1test.domain.paymentService.Entity.Payment;
import com.example.nplus1test.domain.paymentService.repository.JpaPaymentRepository;
import com.example.nplus1test.domain.userLogin.Users.Entity.Users;
import com.example.nplus1test.domain.userLogin.Users.service.userService;
import com.example.nplus1test.domain.userLogin.member.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class requestTossPaymentService {

    private final userService userService;
    private final JpaPaymentRepository paymentRepository;

    public requestTossPaymentService(userService userService, JpaPaymentRepository paymentRepository) {
        this.userService = userService;
        this.paymentRepository = paymentRepository;
    }

    public Payment requestPayment(Payment payment, String userEmail) {
        Users member = userService.searchEmail(userEmail);

        payment.setCustomer(member);
        return paymentRepository.save(payment);
    }

}
