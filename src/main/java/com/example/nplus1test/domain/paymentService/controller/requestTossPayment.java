package com.example.nplus1test.domain.paymentService.controller;

import com.example.nplus1test.config.TossPaymentConfig;
import com.example.nplus1test.domain.paymentService.service.requestTossPaymentService;
import com.example.nplus1test.domain.userLogin.Users.Entity.Users;
import com.example.nplus1test.domain.paymentService.dto.PaymentDto;
import com.example.nplus1test.domain.paymentService.dto.PaymentResDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class requestTossPayment {

    private final requestTossPaymentService paymentService;
    private final TossPaymentConfig tossPaymentConfig;

    public requestTossPayment(requestTossPaymentService paymentService,  TossPaymentConfig tossPaymentConfig) {
        this.paymentService = paymentService;
        this.tossPaymentConfig = tossPaymentConfig;
    }


    @PostMapping("/toss")
    public ResponseEntity requestToss(@AuthenticationPrincipal Users principal, @RequestBody @Valid PaymentDto paymentDto) {
        PaymentResDto paymentResDto = paymentService.requestPayment(paymentDto.toEntity(),
                principal.getEmail()).toPaymentResDto();

        paymentResDto.setSuccessUrl(paymentResDto.getSuccessUrl() == null ? tossPaymentConfig.getSuccessUrl() : paymentResDto.getSuccessUrl());
        paymentResDto.setFailUrl(paymentResDto.getFailUrl() == null ? tossPaymentConfig.getFailUrl() : paymentResDto.getFailUrl());

        return ResponseEntity.ok().body("성공");
    }

}
