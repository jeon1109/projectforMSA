package com.example.nplus1test.domain.PassOAuth.service;

import com.example.nplus1test.domain.PassOAuth.DTO.PhoneAuthResultDto;
import com.example.nplus1test.domain.PassOAuth.config.PassClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PassServiceTest {

    @Mock
    PassClient passClient;

    @InjectMocks
    PassService passService;

    private PassClient.PassResult successResult(Map<String, String> map) {
        return new PassClient.PassResult(true, map, "20250101010101", 0, "OK");
    }

    private PassClient.PassResult failureResult() {
        return new PassClient.PassResult(false, Map.of(), null, -1, "ERROR");
    }

    @Test
    @DisplayName("parseAndValidate - 성공 시 DTO 필드 매핑이 올바르다")
    void parseAndValidate_success() {
        Map<String, String> data = Map.of(
                "REQ_SEQ", "REQ-123",
                "RES_SEQ", "RES-456",
                "AUTH_TYPE", "M",
                "NAME", "홍길동",
                "BIRTHDATE", "19900101",
                "GENDER", "1",
                "DI", "DI123",
                "CI", "CI123",
                "MOBILE_NO", "01012345678",
                "MOBILE_CO", "1"
        );
        when(passClient.decode(anyString())).thenReturn(successResult(data));

        PhoneAuthResultDto dto = passService.parseAndValidate("ENCODED", "REQ-123");

        assertThat(dto.getSRequestNumber()).isEqualTo("REQ-123");
        assertThat(dto.getSResponseNumber()).isEqualTo("RES-456");
        assertThat(dto.getSAuthType()).isEqualTo("M");
        assertThat(dto.getSCipherTime()).isEqualTo("20250101010101");
        assertThat(dto.getName()).isEqualTo("홍길동");
        assertThat(dto.getBirth()).isEqualTo("19900101");
        assertThat(dto.getGender()).isEqualTo("1");
        assertThat(dto.getDupInfo()).isEqualTo("DI123");
        assertThat(dto.getConnInfo()).isEqualTo("CI123");
        assertThat(dto.getPhone()).isEqualTo("01012345678");
        assertThat(dto.getMobileCompany()).isEqualTo("1");
    }

    @Test
    @DisplayName("parseAndValidate - PassClient 실패 시 예외 발생")
    void parseAndValidate_clientFailure() {
        when(passClient.decode(anyString())).thenReturn(failureResult());

        assertThatThrownBy(() -> passService.parseAndValidate("ENC", "REQ-1"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("PASS 처리 실패");
    }

    @Test
    @DisplayName("parseAndValidate - 세션 REQ_SEQ 불일치 시 예외 발생")
    void parseAndValidate_sessionMismatch() {
        Map<String, String> data = Map.of("REQ_SEQ", "REQ-OK");
        when(passClient.decode(anyString())).thenReturn(successResult(data));

        assertThatThrownBy(() -> passService.parseAndValidate("ENC", "REQ-BAD"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("세션값 불일치");
    }

    @Test
    @DisplayName("parseAndValidate - 세션 REQ_SEQ null이면 불일치 검사 없이 통과")
    void parseAndValidate_nullSessionReqSeq() {
        Map<String, String> data = Map.of("REQ_SEQ", "REQ-OK");
        when(passClient.decode(anyString())).thenReturn(successResult(data));

        PhoneAuthResultDto dto = passService.parseAndValidate("ENCODED", null);
        assertThat(dto.getSRequestNumber()).isEqualTo("REQ-OK");
    }
}