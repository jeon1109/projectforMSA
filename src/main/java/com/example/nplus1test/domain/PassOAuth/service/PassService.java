package com.example.nplus1test.domain.PassOAuth.service;

import com.example.nplus1test.domain.PassOAuth.DTO.PhoneAuthResultDto;
import com.example.nplus1test.domain.PassOAuth.config.PassClient;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PassService {

    private final PassClient passClient;

    public PassService(PassClient passClient) {
        this.passClient = passClient;
    }

    public PhoneAuthResultDto parseAndValidate(String encodeData, String sessionReqSeq) {
        PassClient.PassResult result = passClient.decode(encodeData);

        if (!result.isSuccess()) {
            throw new IllegalStateException("PASS 처리 실패: " + result.getMessage());
        }

        Map<String, String> map = result.getData();
        String reqSeq = map.getOrDefault("REQ_SEQ", "");
        if (sessionReqSeq != null && !sessionReqSeq.equals(reqSeq)) {
            throw new IllegalStateException("세션값 불일치 오류입니다.");
        }

        return PhoneAuthResultDto.builder()
                .sRequestNumber(reqSeq)
                .sResponseNumber(map.getOrDefault("RES_SEQ", ""))
                .sAuthType(map.getOrDefault("AUTH_TYPE", ""))
                .sCipherTime(result.getCipherTime())
                .name(map.getOrDefault("NAME", ""))
                .birth(map.getOrDefault("BIRTHDATE", ""))
                .gender(map.getOrDefault("GENDER", ""))
                .dupInfo(map.getOrDefault("DI", ""))
                .connInfo(map.getOrDefault("CI", ""))
                .phone(map.getOrDefault("MOBILE_NO", ""))
                .mobileCompany(map.getOrDefault("MOBILE_CO", ""))
                .build();
    }
}
