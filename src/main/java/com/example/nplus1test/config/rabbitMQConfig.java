package com.example.nplus1test.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
@RequiredArgsConstructor
public class rabbitMQConfig {
    @Value("${rabbitmq.queue.name}")
    private String queue;

    @Value("${rabbitmq.queue.json.name}")
    private String jsonQueue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;

    // value 값 이렇게 쓰자!
    @Value("${app.otp.email.exchange}") String exchangeName;
    @Value("${app.otp.email.routing-key}") String routingEmailKey;
    @Value("${app.otp.email.queue}") String queueName;
    @Value("${app.otp.email.dlx}") String dlxName;
    @Value("${app.otp.email.dlq}") String dlqName;


    // 메인 익스체인지(Direct)
    @Bean
    public DirectExchange emailExchange() {
        return new DirectExchange(exchangeName, true, false);
    }

    // DLX(사망 메시지용)
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(dlxName, true, false);
    }

    // 메인 큐: DLX 연결 + 메시지 영속화
    @Bean
    public Queue emailOtpQueue() {
        return QueueBuilder.durable(queueName)
                // RabbitMQ 큐에 “추가 속성(arguments)”을 달아주는 코드
                .withArgument("x-dead-letter-exchange", dlxName)
                .withArgument("x-dead-letter-routing-key", "email.otp.dead")
                .build();
    }

    // DLQ
    @Bean
    public Queue emailOtpDLQ() {
        return QueueBuilder.durable(dlqName).build();
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder.bind(emailOtpQueue()).to(emailExchange()).with(routingKey);
    }

    @Bean
    public Binding emailDLQBinding() {
        return BindingBuilder.bind(emailOtpDLQ()).to(deadLetterExchange()).with("email.otp.dead");
    }



    @Bean
    public Queue queue() {
        return new Queue(queue);
    }

    @Bean
    public Queue jsonQueue() {
        return new Queue(jsonQueue);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    // 라우팅 키를 이용해서 큐와 EXCHANGE를 바인딩한다
    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(routingKey);
    }

    @Bean
    public Binding jsonBinding() {
        return BindingBuilder
                .bind(jsonQueue())
                .to(exchange())
                .with(routingJsonKey);
    }
    // jackson으로 컨버팅하게 도와주네
    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
    
}
