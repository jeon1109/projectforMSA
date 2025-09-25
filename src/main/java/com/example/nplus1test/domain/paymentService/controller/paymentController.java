package com.example.nplus1test.domain.paymentService.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller("/payView")
public class paymentController {

    @GetMapping("/test")
    public String test() {

        return "test";
    }

}
