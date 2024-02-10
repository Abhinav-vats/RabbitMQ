package com.practice.message.queue.RabbitMQ.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

@Configuration
public class RabbitMQConfig {

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    @Bean
    public Queue createUserRegistrationQueue() {
        return QueueBuilder.durable("user-registration")
                .withArgument("x-dead-letter-exchange","registration-failure")
                .withArgument("x-dead-letter-routing-key","fall-back")
                .build();
    }

    @Bean
    public RetryOperationsInterceptor retryInterceptorFallback(){
        return RetryInterceptorBuilder.stateless().maxAttempts(3)
                .backOffOptions(2000, 2.0, 100000)
                .recoverer(new RejectAndDontRequeueRecoverer())
                .build();
    }

    @Bean
    public Declarables createPostRegistartionSchema(){

        return new Declarables(
                new FanoutExchange("post-registration"),
                new Queue("send-email" ),
                new Queue("send-sms"),
                new Binding("send-email", Binding.DestinationType.QUEUE, "post-registration", "send-email", null),
                new Binding("send-sms", Binding.DestinationType.QUEUE, "post-registration", "send-sms", null));

    }

    @Bean
    public RetryOperationsInterceptor retryInterceptor(){
        return RetryInterceptorBuilder.stateless().maxAttempts(3)
                .backOffOptions(2000, 2.0, 100000)
                .build();
    }


    @Bean(name = "registrationListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, cachingConnectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        factory.setAdviceChain(retryInterceptor());
        return factory;
    }

    @Bean
    public Declarables createDeadLetterSchema(){
        return new Declarables(
                new DirectExchange("registration-failure"),
                new Queue("fall-back-registration"),
                new Binding("fall-back-registration", Binding.DestinationType.QUEUE,"registration-failure", "fall-back", null)
        );
    }
}
