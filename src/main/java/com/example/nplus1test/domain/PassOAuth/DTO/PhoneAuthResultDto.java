package com.example.nplus1test.domain.PassOAuth.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class PhoneAuthResultDto {
    // 세션/무결성 관련
    private final String sRequestNumber;   // REQ_SEQ
    private final String sResponseNumber;  // RES_SEQ
    private final String sAuthType;        // AUTH_TYPE
    private final String sCipherTime;      // 복호화된 시간(yyyyMMddHHmmss 등)

    // 사용자 정보
    private final String name;             // NAME
    private final String birth;            // BIRTHDATE (yyyyMMdd)
    private final String gender;           // GENDER (기관별 약속: "1"/"0" 등)

    // 중복/연계 식별자
    private final String dupInfo;          // DI
    private final String connInfo;         // CI

    // 휴대폰 정보
    private final String phone;            // MOBILE_NO
    private final String mobileCompany;    // MOBILE_CO (1:SKT, 2:KT, 3:LGU+ 등)
}
