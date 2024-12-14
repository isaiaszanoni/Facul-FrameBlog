package com.descomplica.FrameBlog.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${FrameBlog.rabbitmq.queue-comment}")
    String queueName;

    @Value("${FrameBlog.rabbitmq.exchange}")
    String exchange;

    @Value("${FrameBlog.rabbitmq.routingkey}")
    String routingkey;

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with(routingkey);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
