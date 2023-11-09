package banz.ai.marketing.bot.userfeedback.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RmqConfig {

  @Value("${queues.feedback-post}")
  String feedbackPostQueue;

  @Bean
  MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  Queue feedbackPostQueue() {
    return new Queue(feedbackPostQueue, true, false, false);
  }
}
