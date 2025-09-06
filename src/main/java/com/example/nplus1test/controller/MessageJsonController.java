package com.example.nplus1test.controller;

import com.example.nplus1test.dto.User;
import com.example.nplus1test.publisher.RabbitMQJsonProducer;
import com.example.nplus1test.publisher.RabbitMQProducer;
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
        jsonProducer.sendjsonMessage(user);
        return ResponseEntity.ok("Json Message sent to RabbitMQ ...");
    }
}
