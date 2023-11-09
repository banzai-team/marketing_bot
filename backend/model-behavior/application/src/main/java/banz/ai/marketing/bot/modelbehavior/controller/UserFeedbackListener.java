package banz.ai.marketing.bot.modelbehavior.controller;

import banz.ai.marketing.bot.commons.mq.UserFeedbackToApplyDTO;
import banz.ai.marketing.bot.modelbehavior.feedback.FeedbackHandler;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class UserFeedbackListener {

    private static Logger logger =  LoggerFactory.getLogger(UserFeedbackListener.class);

    private final FeedbackHandler feedbackHandler;

    @RabbitListener(queues = "${queues.feedback}", ackMode = "MANUAL")
    public void processFeedback(UserFeedbackToApplyDTO message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        logger.debug("Received feedback message: %s".formatted(message));
        feedbackHandler.handleUserFeedback(message);
        channel.basicAck(tag, false);
    }
}
