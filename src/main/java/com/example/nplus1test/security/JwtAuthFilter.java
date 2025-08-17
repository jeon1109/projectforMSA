package com.example.nplus1test.security;

import com.example.nplus1test.token.TokenStore;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final TokenStore tokenStore;

    public JwtAuthFilter(JwtProvider jwtProvider, TokenStore tokenStore) {
        this.jwtProvider = jwtProvider;
        this.tokenStore = tokenStore;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String auth = request.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            try {
                Jws<Claims> jws = jwtProvider.parse(token);
                Claims c = jws.getPayload();

                String jti = c.getId();
                if (jti != null && tokenStore.isBlacklisted(jti)) {
                    filterChain.doFilter(request, response);
                    return; // 블랙리스트 토큰 거부
                }

                Long userId = Long.valueOf(c.getSubject());
                String username = c.get("username", String.class);
                List<String> roles = c.get("roles", List.class);
                Set<SimpleGrantedAuthority> auths = roles == null ? Set.of() :
                        roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r)).collect(Collectors.toSet());

                var authentication = new UsernamePasswordAuthenticationToken(username, null, auths);
                authentication.setDetails(userId);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // 토큰 무효/만료 시 그냥 통과 -> 결국 401/403 처리
            }
        }
        filterChain.doFilter(request, response);
    }
}