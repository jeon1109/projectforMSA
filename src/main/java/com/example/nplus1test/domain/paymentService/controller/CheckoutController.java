package com.example.nplus1test.domain.paymentService.controller;

import com.example.nplus1test.domain.paymentService.common.widgetAdpator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@widgetAdpator
@RequestMapping("/v1/toss")
public class CheckoutController {

    @GetMapping("/checkouts")
    public String checkout() {

        return "checkout"; // 정적 파일로 보냄
    }
}
