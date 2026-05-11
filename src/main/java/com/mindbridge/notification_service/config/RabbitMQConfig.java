package com.mindbridge.notification_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "mindbridge.exchange";

    // Queues
    public static final String OFFER_CREATED_QUEUE = "offer.created.queue";
    public static final String OFFER_CANCELLED_QUEUE = "offer.cancelled.queue";
    public static final String OFFER_TAKEN_QUEUE = "offer.taken.queue";
    public static final String SESSION_REQUESTED_QUEUE = "session.requested.queue";
    public static final String SESSION_APPROVED_QUEUE = "session.approved.queue";
    public static final String SESSION_ENROLLED_QUEUE = "session.enrolled.queue";
    public static final String SESSION_CANCELLED_QUEUE = "session.cancelled.queue";

    @Bean
    public TopicExchange mindbridgeExchange() {
        return new TopicExchange(EXCHANGE);
    }

    // Offer queues
    @Bean public Queue offerCreatedQueue() { return new Queue(OFFER_CREATED_QUEUE, true); }
    @Bean public Queue offerCancelledQueue() { return new Queue(OFFER_CANCELLED_QUEUE, true); }
    @Bean public Queue offerTakenQueue() { return new Queue(OFFER_TAKEN_QUEUE, true); }

    // Group session queues
    @Bean public Queue sessionRequestedQueue() { return new Queue(SESSION_REQUESTED_QUEUE, true); }
    @Bean public Queue sessionApprovedQueue() { return new Queue(SESSION_APPROVED_QUEUE, true); }
    @Bean public Queue sessionEnrolledQueue() { return new Queue(SESSION_ENROLLED_QUEUE, true); }
    @Bean public Queue sessionCancelledQueue() { return new Queue(SESSION_CANCELLED_QUEUE, true); }

    // Bindings
    @Bean public Binding offerCreatedBinding() { return BindingBuilder.bind(offerCreatedQueue()).to(mindbridgeExchange()).with("offer.created"); }
    @Bean public Binding offerCancelledBinding() { return BindingBuilder.bind(offerCancelledQueue()).to(mindbridgeExchange()).with("offer.cancelled"); }
    @Bean public Binding offerTakenBinding() { return BindingBuilder.bind(offerTakenQueue()).to(mindbridgeExchange()).with("offer.taken"); }
    @Bean public Binding sessionRequestedBinding() { return BindingBuilder.bind(sessionRequestedQueue()).to(mindbridgeExchange()).with("session.requested"); }
    @Bean public Binding sessionApprovedBinding() { return BindingBuilder.bind(sessionApprovedQueue()).to(mindbridgeExchange()).with("session.approved"); }
    @Bean public Binding sessionEnrolledBinding() { return BindingBuilder.bind(sessionEnrolledQueue()).to(mindbridgeExchange()).with("session.enrolled"); }
    @Bean public Binding sessionCancelledBinding() { return BindingBuilder.bind(sessionCancelledQueue()).to(mindbridgeExchange()).with("session.cancelled"); }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}