package com.example.nplus1test.domain.userLogin.common.status;

public enum Gender {
    MAN("남"),
    WOMAN("여");

    private final String desc;

    Gender(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}

