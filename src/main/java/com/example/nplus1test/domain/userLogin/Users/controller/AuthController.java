package com.example.nplus1test.domain.userLogin.Users.controller;


import com.example.nplus1test.domain.country.repository.UserRepository;
import com.example.nplus1test.domain.userLogin.Users.Entity.Users;
import com.example.nplus1test.domain.userLogin.Users.dto.AuthDtos;
import com.example.nplus1test.domain.userLogin.Users.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody AuthDtos.SignupReq req) {
        if (req.email() == null || req.password() == null || req.password().length() < 8)
            return ResponseEntity.badRequest().body("invalid");
        if (userRepository.existsByEmail(req.email()))
            return ResponseEntity.status(409).body("email exists");

        Users u = Users.builder()
                .email(req.email())
                .password(encoder.encode(req.password()))
                .build();

        userRepository.save(u);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDtos.LoginReq req) {
        var user = userRepository.findByEmail(req.email()).orElse(null);
        if (user == null || !encoder.matches(req.password(), user.getPassword()))
            return ResponseEntity.status(401).body("bad creds");

        String access = jwtService.generateAccess(user);
        String refresh = jwtService.generateRefresh(user);
        return ResponseEntity.ok(new AuthDtos.TokenRes(access, refresh));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody String refreshToken) {
        try {
            var claims = jwtService.parseRefresh(refreshToken).getBody();
            Long userId = Long.valueOf(claims.getSubject());
            var user = userRepository.findById(userId).orElse(null);
            if (user == null) return ResponseEntity.status(401).body("invalid refresh");

            String newAccess = jwtService.generateAccess(user);
            String newRefresh = jwtService.generateRefresh(user); // 회전
            return ResponseEntity.ok(new AuthDtos.TokenRes(newAccess, newRefresh));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("invalid refresh");
        }
    }
}
