package com.example.nplus1test.domain.userLogin.common.status;

public enum ResultCode {
    SUCCESS("정상 처리 되었습니다."),
    ERROR("에러가 발생했습니다.");

    private final String desc;

    ResultCode(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
