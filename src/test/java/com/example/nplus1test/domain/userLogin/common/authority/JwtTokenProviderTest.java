package com.example.nplus1test.domain.userLogin.common.authority;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Base64;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider providerWithKey() {
        JwtTokenProvider provider = new JwtTokenProvider();
        // 32 bytes -> base64
        String raw = "01234567890123456789012345678901";
        String base64 = Base64.getEncoder().encodeToString(raw.getBytes());
        ReflectionTestUtils.setField(provider, "secretKey", base64);
        provider.initKey();
        return provider;
    }

    @AfterEach
    void tearDown() {
        // Ensure no residual from security context in other tests
        org.springframework.security.core.context.SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("JWT 토큰 생성 및 검증/복원 성공")
    void createValidateRoundTrip() {
        JwtTokenProvider provider = providerWithKey();

        var auth = new UsernamePasswordAuthenticationToken(
                "john.doe@example.com",
                "",
                List.of(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"))
        );

        Tokeninfo token = provider.createToken(auth);
        assertThat(token).isNotNull();
        assertThat(token.getAccessToken()).isNotBlank();
        assertThat(token.getGrantType()).isEqualTo("Bearer");

        assertThat(provider.validateToken(token.getAccessToken())).isTrue();

        var restored = provider.getAuthentication(token.getAccessToken());
        assertThat(restored.getName()).isEqualTo("john.doe@example.com");
        assertThat(restored.getAuthorities()).extracting("authority")
                .contains("ROLE_USER", "ROLE_ADMIN");
    }

    @Test
    @DisplayName("손상된 토큰은 검증 실패")
    void tamperedToken() {
        JwtTokenProvider provider = providerWithKey();

        var auth = new UsernamePasswordAuthenticationToken("user@ex.com", "", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        Tokeninfo token = provider.createToken(auth);

        String tampered = token.getAccessToken() + "a";
        assertThat(provider.validateToken(tampered)).isFalse();
    }
}