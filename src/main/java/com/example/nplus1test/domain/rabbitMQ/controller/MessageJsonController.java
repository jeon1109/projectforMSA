package com.example.nplus1test.domain.rabbitMQ.controller;

import com.example.nplus1test.domain.userLogin.dto.User;
import com.example.nplus1test.domain.rabbitMQ.publisher.RabbitMQJsonProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class MessageJsonController {
    private RabbitMQJsonProducer jsonProducer;

    public MessageJsonController(RabbitMQJsonProducer jsonProducer) {
        this.jsonProducer = jsonProducer;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestBody User user) {
        // 파라미터로 보낸 값을 메신저에 담는다
        jsonProducer.sendjsonMessage(user);
        return ResponseEntity.ok("Json Message sent to RabbitMQ ...");
    }
}
