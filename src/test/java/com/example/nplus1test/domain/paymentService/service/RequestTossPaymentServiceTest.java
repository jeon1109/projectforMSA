package com.example.nplus1test.domain.paymentService.service;

import com.example.nplus1test.domain.paymentService.Entity.Payment;
import com.example.nplus1test.domain.paymentService.annotation.PayType;
import com.example.nplus1test.domain.paymentService.repository.JpaPaymentRepository;
import com.example.nplus1test.domain.userLogin.Users.Entity.Users;
import com.example.nplus1test.domain.userLogin.Users.service.userService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RequestTossPaymentServiceTest {

    @Test
    @DisplayName("결제 요청 시 사용자 연관이 저장되고 저장소에 persist 된다")
    void requestPayment() {
        userService userSvc = mock(userService.class);
        JpaPaymentRepository repo = mock(JpaPaymentRepository.class);

        Users member = Users.builder().email("member@ex.com").password("pw").build();
        when(userSvc.searchEmail("member@ex.com")).thenReturn(member);
        when(repo.save(any(Payment.class))).thenAnswer(inv -> inv.getArgument(0));

        requestTossPaymentService service = new requestTossPaymentService(userSvc, repo);

        Payment payment = Payment.builder()
                .payType(PayType.card)
                .amount(1000L)
                .orderName("테스트주문")
                .orderId("ORDER-1")
                .paySuccessYN(false)
                .cancelYN(false)
                .build();

        Payment saved = service.requestPayment(payment, "member@ex.com");

        assertThat(saved.getCustomer()).isSameAs(member);
        verify(repo, times(1)).save(any(Payment.class));
    }
}