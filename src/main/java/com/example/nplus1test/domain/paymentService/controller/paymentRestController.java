package com.example.nplus1test.domain.paymentService.controller;

import com.example.nplus1test.config.TossProps;
import com.example.nplus1test.domain.country.dto.ConfirmRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@RestController("/pay")
public class paymentRestController {

    private final TossProps toss;
    private final RestClient http;

    public paymentRestController(TossProps toss) {
        this.toss = toss;
        this.http = RestClient.create();
    }

    /** 성공 리다이렉트: 쿼리에서 paymentKey/orderId/amount 수집 → 서버에서 confirm */
    @GetMapping("/success")
    public ResponseEntity<?> success(
            @RequestParam String paymentKey,
            @RequestParam String orderId,
            @RequestParam Long amount
    ) {
        ConfirmRequest body = new ConfirmRequest(paymentKey, orderId, amount);
        Map<String, Object> confirmed = confirmPayment(body);

        return ResponseEntity.ok(confirmed);
    }

    /** 실패 리다이렉트: 에러 정보 확인용 */
    @GetMapping("/fail")
    public ResponseEntity<?> fail(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String message,
            @RequestParam(required = false) String orderId
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("code", code,
                        "message", message,
                        "orderId", orderId));
    }

    /** 내부 confirm 호출 */
    @PostMapping("/api/payments/confirm")
    public Map<String, Object> confirm(@RequestBody ConfirmRequest req) {

        return confirmPayment(req);
    }

    private Map<String, Object> confirmPayment(ConfirmRequest req) {
        // 토스에 v1로 보낸다
        String url = toss.getApiBase() + "/v1/payments/confirm";

        // Basic 인증: base64("SECRET_KEY:")
        String credentials = toss.getSecretKey() + ":";
        String basic = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        // JSON 바디
        String json = """
            {
              "paymentKey":"%s",
              "orderId":"%s",
              "amount":%d
            }
            """.formatted(req.getPaymentKey(), req.getOrderId(), req.getAmount());

        return http.post()
                .uri(url)
                .header(HttpHeaders.AUTHORIZATION, basic)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(json)
                .retrieve()
                .body(Map.class);
    }

}
