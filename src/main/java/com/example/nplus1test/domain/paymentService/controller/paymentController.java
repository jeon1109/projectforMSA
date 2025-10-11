package com.example.nplus1test.domain.paymentService.controller;

import com.example.nplus1test.domain.paymentService.common.widgetAdpator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@widgetAdpator
@RequestMapping("/v1/toss")
public class paymentController {

    @GetMapping("/checkout")
    public String checkout() {

        return "checkout";
    }

    @GetMapping("/tossGood")
    public String tossGood() {

        return "tossGood";
    }
    @GetMapping("/sucess")
    public String sucess() {

        return "sucess";
    }

    @GetMapping("/fail")
    public String fail() {

        return "fail";
    }

}
