package com.example.nplus1test.token;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class TokenStoreTest {

    @Test
    @DisplayName("refreshToken 저장은 key 규칙을 준수한다 (refresh:{userId})")
    void saveRefreshToken() {
        StringRedisTemplate redis = mock(StringRedisTemplate.class);
        @SuppressWarnings("unchecked")
        ValueOperations<String, String> ops = mock(ValueOperations.class);
        when(redis.opsForValue()).thenReturn(ops);

        TokenStore store = new TokenStore(redis);
        store.saveRefreshToken(123L, "REFRESH_TOKEN", Duration.ofMinutes(5));

        verify(ops, times(1)).set(eq("refresh:123"), eq("REFRESH_TOKEN"));
    }

    @Test
    @DisplayName("접근 토큰 블랙리스트 등록 시 TTL 이 반영된다")
    void blacklistAccessToken() {
        StringRedisTemplate redis = mock(StringRedisTemplate.class);
        @SuppressWarnings("unchecked")
        ValueOperations<String, String> ops = mock(ValueOperations.class);
        when(redis.opsForValue()).thenReturn(ops);

        TokenStore store = new TokenStore(redis);
        Duration ttl = Duration.ofSeconds(10);
        store.blacklistAccessToken("JTI-1", ttl);

        verify(ops, times(1)).set(eq("blacklist:JTI-1"), eq("1"), eq(ttl));
    }

    @Test
    @DisplayName("블랙리스트 조회는 hasKey 를 통해 수행된다")
    void isBlacklisted() {
        StringRedisTemplate redis = mock(StringRedisTemplate.class);
        when(redis.hasKey("blacklist:JTI-2")).thenReturn(true);

        TokenStore store = new TokenStore(redis);
        assertThat(store.isBlacklisted("JTI-2")).isTrue();
    }
}