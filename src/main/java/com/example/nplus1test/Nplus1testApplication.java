package com.example.nplus1test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 1. 스프링 설정 클래스를 정의
// 2. 컴포넌트 스캔과 스프링부트 자동설정을 실행
// 3. @Component scan 기능을 가짐 : 빈을 찾아 스프링 ㅂ콘텍스트에 자동으로 등록
// 4. @EnableAutoConfiguration : 스크링부트에서 제공하는 자동 설정기능 활성화
// 5. @Configuration : 빈을 정의하는 클래스임을 나타낸다
@SpringBootApplication
public class Nplus1testApplication {
    // 1.
    public static void main(String[] args) {
        SpringApplication.run(Nplus1testApplication.class, args);
    }

}
