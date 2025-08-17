package com.example.nplus1test.domain.country.service;

import com.example.nplus1test.domain.country.dto.*;
import com.example.nplus1test.security.JwtProvider;
import com.example.nplus1test.token.TokenStore;
import com.example.nplus1test.domain.country.entity.UserEntity;
import com.example.nplus1test.domain.country.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtProvider jwt;
    private final TokenStore store;

    public AuthService(UserRepository users, PasswordEncoder encoder, JwtProvider jwt, TokenStore store) {
        this.users = users; this.encoder = encoder; this.jwt = jwt; this.store = store;
    }

    public void signup(UserDto.SignupRequest req) {
        if (users.existsByUsername(req.username()))
            throw new IllegalArgumentException("이미 존재하는 사용자명입니다.");
        String hash = encoder.encode(req.password());
        users.save(new UserEntity(req.username(), hash, Set.of("USER")));
    }

    public UserDto.TokenResponse login(UserDto.LoginRequest req) {
        var user = users.findByUsername(req.username()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자"));
        if (!encoder.matches(req.password(), user.getPassword()))
            throw new IllegalArgumentException("비밀번호 불일치");

        String access = jwt.createAccessToken(user.getId(), user.getUsername(), user.getRoles());
        String refresh = jwt.createRefreshToken(user.getId());
        store.saveRefreshToken(user.getId(), refresh, Duration.ofDays(7));
        return new UserDto.TokenResponse(access, refresh);
    }

    public UserDto.TokenResponse refresh(UserDto.RefreshRequest req) {
        var jws = jwt.parse(req.refreshToken());
        Long userId = Long.valueOf(jws.getPayload().getSubject());
        String saved = store.getRefreshToken(userId);
        if (saved == null || !saved.equals(req.refreshToken()))
            throw new IllegalArgumentException("리프레시 토큰 무효");

        var user = users.findById(userId).orElseThrow();
        String access = jwt.createAccessToken(user.getId(), user.getUsername(), user.getRoles());
        String newRefresh = jwt.createRefreshToken(user.getId());
        store.saveRefreshToken(user.getId(), newRefresh, Duration.ofDays(7));
        return new UserDto.TokenResponse(access, newRefresh);
    }

    public void logout(String accessToken) {
        var jws = jwt.parse(accessToken);
        var claims = jws.getPayload();
        String jti = claims.getId();
        long seconds = (claims.getExpiration().getTime() - System.currentTimeMillis()) / 1000;
        if (jti != null && seconds > 0) {
            store.blacklistAccessToken(jti, Duration.ofSeconds(seconds));
        }
        Long userId = Long.valueOf(claims.getSubject());
        store.deleteRefreshToken(userId);
    }
}
