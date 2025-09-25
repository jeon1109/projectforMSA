package com.example.nplus1test.domain.rabbitMQ.publisher;

import com.example.nplus1test.domain.userLogin.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonProducer {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;

    private RabbitTemplate rabbitTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonProducer.class);

    public RabbitMQJsonProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendjsonMessage(User user) {
        LOGGER.info(String.format("Json message send -> %s", user.toString()));
        // 익스체인저와 라우팅 키를 보낸다
        rabbitTemplate.convertAndSend(exchange, routingJsonKey, user);
    }

}
