package com.example.nplus1test.token;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.time.Duration;

@Component
public class TokenStore {
    private final StringRedisTemplate redis;

    public TokenStore(StringRedisTemplate redis) { this.redis = redis; }

    // key 규칙 예: refresh:{userId} -> refreshToken
    public void saveRefreshToken(Long userId, String refreshToken, Duration ttl) {
        redis.opsForValue().set("refresh:" + userId, refreshToken, ttl);
    }

    public String getRefreshToken(Long userId) {
        return redis.opsForValue().get("refresh:" + userId);
    }

    public void deleteRefreshToken(Long userId) {
        redis.delete("refresh:" + userId);
    }

    // 블랙리스트: blacklist:{jti} -> "1"
    public void blacklistAccessToken(String jti, Duration ttl) {
        redis.opsForValue().set("blacklist:" + jti, "1", ttl);
    }

    public boolean isBlacklisted(String jti) {
        return redis.hasKey("blacklist:" + jti);
    }
}
