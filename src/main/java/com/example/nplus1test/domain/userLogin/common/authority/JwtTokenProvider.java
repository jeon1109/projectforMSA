package com.example.nplus1test.domain.userLogin.common.authority;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    // 12시간
    private static final long EXPIRATION_MILLISECONDS = 1000L * 60 * 60 * 12;

    @Value("${jwt.secret}")
    private String secretKey; // Base64-encoded secret

    private Key key;

    @PostConstruct
    public void initKey() {
        // secretKey가 Base64 인코딩되어 있다는 전제 (요청 주신 코드와 동일)
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    /**
     * 토큰 생성
     */
    public Tokeninfo createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();
        Date accessExpiration = new Date(now.getTime() + EXPIRATION_MILLISECONDS);

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setIssuedAt(now)
                .setExpiration(accessExpiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return new Tokeninfo("Bearer", accessToken);
    }

    /**
     * 토큰 정보로 Authentication 생성
     */
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        Object authClaim = claims.get("auth");
        if (authClaim == null) {
            throw new RuntimeException("잘못된 토큰 입니다.");
        }

        Collection<GrantedAuthority> authorities = java.util.Arrays.stream(authClaim.toString().split(","))
                .filter(s -> !s.isBlank())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    /**
     * 토큰 검증
     */
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (SecurityException e) {
            // Invalid JWT Signature/Token
            System.out.println(e.getMessage());
        } catch (MalformedJwtException e) {
            // Invalid JWT Token
            System.out.println(e.getMessage());
        } catch (ExpiredJwtException e) {
            // Expired JWT Token
            System.out.println(e.getMessage());
        } catch (UnsupportedJwtException e) {
            // Unsupported JWT Token
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            // JWT claims string is empty
            System.out.println(e.getMessage());
        } catch (Exception e) {
            // 기타 예외
            System.out.println(e.getMessage());
        }
        return false;
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 필요 시 사용하던 TokenInfo DTO가 없다면 아래 간단 버전을 사용하세요.
    // public static class TokenInfo {
    //     private final String grantType;
    //     private final String accessToken;
    //
    //     public TokenInfo(String grantType, String accessToken) {
    //         this.grantType = grantType;
    //         this.accessToken = accessToken;
    //     }
    //
    //     public String getGrantType() { return grantType; }
    //     public String getAccessToken() { return accessToken; }
    // }
}
