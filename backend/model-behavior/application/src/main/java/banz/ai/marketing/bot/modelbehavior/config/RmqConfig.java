package banz.ai.marketing.bot.modelbehavior.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RmqConfig {

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Value("${queues.feedback-post}")
    String feedbackPostQueue;

    @Value("${queues.feedback-response}")
    String feedbackResponseQueue;

    @Value("${queues.behavior}")
    String behaviorQueue;

    @Bean
    public Queue feedbackPostQueue() {
        return new Queue(feedbackPostQueue, true, false, false);
    }

    @Bean
    public Queue feedbackResponseQueue() {
        return new Queue(feedbackResponseQueue, true, false, false);
    }

    @Bean
    public Queue behaviorQueue() {
        return new Queue(behaviorQueue, true, false, false);
    }
}
