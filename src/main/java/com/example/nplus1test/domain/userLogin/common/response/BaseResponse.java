package com.example.nplus1test.domain.userLogin.common.response;

import com.example.nplus1test.domain.userLogin.common.status.ResultCode;

public class BaseResponse<T> {

    private String resultCode;
    private T data;
    private String message;

    // 기본 생성자 (Jackson 역직렬화용)
    public BaseResponse() {
        this.resultCode = ResultCode.SUCCESS.name();
        this.message = ResultCode.SUCCESS.getDesc();
    }

    // 전체 필드 생성자
    public BaseResponse(String resultCode, T data, String message) {
        this.resultCode = resultCode;
        this.data = data;
        this.message = message;
    }

    // 데이터만 받는 생성자 (성공 응답 기본값)
    public BaseResponse(T data) {
        this.resultCode = ResultCode.SUCCESS.name();
        this.data = data;
        this.message = ResultCode.SUCCESS.getDesc();
    }

    // Getter / Setter
    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}