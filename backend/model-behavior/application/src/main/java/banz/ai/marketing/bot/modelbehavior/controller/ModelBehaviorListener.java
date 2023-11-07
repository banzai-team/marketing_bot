package banz.ai.marketing.bot.modelbehavior.controller;

import banz.ai.marketing.bot.commons.ModelBehaviorDTO;
import banz.ai.marketing.bot.modelbehavior.behavior.BehaviorHandler;
import banz.ai.marketing.bot.modelbehavior.behavoir.exception.InvalidModelBehaviorException;
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
public class ModelBehaviorListener {
    private static Logger logger =  LoggerFactory.getLogger(UserFeedbackListener.class);

    private final BehaviorHandler behaviorHandler;

    @RabbitListener(queues = "${queues.behavior}", ackMode = "MANUAL")
    public void processBehavior(ModelBehaviorDTO message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        logger.debug("Received behavior message: %s".formatted(message));
        try {
            behaviorHandler.handleBehavior(message);
        } catch (InvalidModelBehaviorException exception) {
            // do nothing about it
            logger.error("Invalid model behavior received::%s".formatted(message), exception);
        }
        channel.basicAck(tag, false);
    }
}
