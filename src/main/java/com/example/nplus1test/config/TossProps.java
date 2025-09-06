package com.example.nplus1test.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
// application.yml (또는 application.properties) 에서
// 시작하는 설정 값들을 한꺼번에 자바 객체(클래스)로 바인딩해주는 기능
@ConfigurationProperties(prefix = "toss")
public class TossProps {
    // 프로퍼티에 등록된 키를 부른다
    private String clientKey;
    private String secretKey;
    private String apiBase;
    private String successUrl;
    private String failUrl;

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getApiBase() {
        return apiBase;
    }

    public void setApiBase(String apiBase) {
        this.apiBase = apiBase;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getFailUrl() {
        return failUrl;
    }

    public void setFailUrl(String failUrl) {
        this.failUrl = failUrl;
    }
}
