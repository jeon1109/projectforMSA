package com.example.nplus1test.domain.PassOAuth.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

@Component
@Profile("mock-pass") // mock-pass 프로필일 때만 활성
public class FakePassClient implements PassClient{
    // 오버라이드
    @Override
    public PassResult decode(String encodeData) {
        // encodeData는 무시하고, PASS가 주는 키들을 그대로 흉내 냅니다.
        String reqSeq = "REQ-" + UUID.randomUUID().toString().substring(0, 8); // req
        String resSeq = "RES-" + UUID.randomUUID().toString().substring(0, 8); // res

        Map<String, String> data = Map.of(
                "REQ_SEQ", reqSeq,
                "RES_SEQ", resSeq,
                "AUTH_TYPE", "M",              // (예) 휴대폰 본인인증
                "NAME", "모킹사용자",
                "BIRTHDATE", "19900101",
                "GENDER", "1",                 // 1:남 0:여 (기관 설정에 따라 다를 수 있음)
                "DI", UUID.randomUUID().toString().replace("-", ""),
                "CI", UUID.randomUUID().toString().replace("-", ""),
                "MOBILE_NO", "01012345678",
                "MOBILE_CO", "1"               // 1:SKT 2:KT 3:LGU+ (예시)
        );
        // 목업 한 시간
        String cipherTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return new PassResult(true, data, cipherTime, 0, "MOCK_OK");
    }

}
