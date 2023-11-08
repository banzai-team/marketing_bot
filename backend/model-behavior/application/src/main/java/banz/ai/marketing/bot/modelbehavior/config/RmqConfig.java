package banz.ai.marketing.bot.modelbehavior.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RmqConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port:5672}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Value("${queues.feedback}")
    String feedbackQueue;

    @Value("${queues.behavior}")
    String behaviorQueue;

    @Bean
    public Queue feedbackQueue() {
        return new Queue(feedbackQueue, true, false, false);
    }

    @Bean
    public Queue behaviorQueue() {
        return new Queue(behaviorQueue, true, false, false);
    }
}
