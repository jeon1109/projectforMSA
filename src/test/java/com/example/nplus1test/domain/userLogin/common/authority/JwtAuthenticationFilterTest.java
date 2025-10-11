package com.example.nplus1test.domain.userLogin.common.authority;

import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Authorization 헤더의 Bearer 토큰을 사용해 SecurityContext 가 설정된다")
    void doFilter_setsAuthentication() throws Exception {
        JwtTokenProvider provider = mock(JwtTokenProvider.class);
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(provider);

        MockHttpServletRequest req = new MockHttpServletRequest();
        req.addHeader("Authorization", "Bearer token-123");
        MockHttpServletResponse res = new MockHttpServletResponse();
        FilterChain chain = Mockito.mock(FilterChain.class);

        when(provider.validateToken("token-123")).thenReturn(true);
        when(provider.getAuthentication("token-123"))
                .thenReturn(new UsernamePasswordAuthenticationToken("user@ex.com", "", java.util.List.of()));

        filter.doFilter(req, res, chain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication().getName()).isEqualTo("user@ex.com");

        verify(chain).doFilter(req, res);
    }

    @Test
    @DisplayName("Authorization 헤더가 없으면 인증 컨텍스트를 설정하지 않는다")
    void doFilter_noHeader() throws Exception {
        JwtTokenProvider provider = mock(JwtTokenProvider.class);
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(provider);

        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        FilterChain chain = Mockito.mock(FilterChain.class);

        filter.doFilter(req, res, chain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(chain).doFilter(req, res);
        verifyNoInteractions(provider);
    }
}