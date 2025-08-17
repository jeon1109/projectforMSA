package com.example.nplus1test.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/me")
    public String me() { return "ok - user"; }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String admin() { return "ok - admin"; }
}
