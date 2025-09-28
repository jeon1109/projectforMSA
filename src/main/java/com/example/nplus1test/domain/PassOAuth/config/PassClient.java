package com.example.nplus1test.domain.PassOAuth.config;

import java.util.Map;

public interface PassClient {
    // 받아온 데이터를 클래스에서 동작
    PassResult decode(String encodeData);
    
    // 인터페이스 안에 decode를 사용하려고 클래스로 만듬 (이게 맞나?)
    class PassResult {
        private final boolean success;
        private final Map<String, String> data; // NAME, BIRTHDATE, GENDER, MOBILE_NO
        private final String cipherTime;
        private final int code; // 0 성공, 음수 에러 코드
        private final String message;

        // DTO 라고 생각하자
        public PassResult(boolean success, Map<String, String> data, String cipherTime, int code, String message) {
            this.success = success;
            this.data = data;
            this.cipherTime = cipherTime;
            this.code = code;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }
        public Map<String, String> getData() {
            return data;
        }
        // 암호화된 데이터 생성 시각
        public String getCipherTime() {
            return cipherTime;
        }
        public int getCode() {
            return code;
        }
        public String getMessage() {
            return message;
        }
    }
}
