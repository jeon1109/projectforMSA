package com.example.nplus1test.domain.paymentService.annotation;

import lombok.Getter;

@Getter
public enum PayType {
    card("카드"),
    cash("현금"),
    point("포인트");

    private final String descrption;
    // 생성자는 필수지! (데이터 변수 생성)
    PayType(String descrption) {
        this.descrption = descrption;
    }
}
