package banz.ai.marketing.bot.modelbehavior.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserFeedbackListener {

    private static Logger logger =  LoggerFactory.getLogger(UserFeedbackListener.class);

    @RabbitListener(queues = "${queues.feedback}")
    public void processFeedback(String message) {
        logger.debug("Received feedback message: %s".formatted(message));
        // System.out.printf("Received from myQueue : %s ", new String(message.getBytes()));
    }
}
