package banz.ai.marketing.bot.modelinterceptor.core;

import banz.ai.marketing.bot.commons.ModelBehaviorDTO;
import banz.ai.marketing.bot.commons.ModelRequestDTO;
import banz.ai.marketing.bot.commons.ModelResponseDTO;
import banz.ai.marketing.bot.modelinterceptor.dto.ClientModelRequest;
import banz.ai.marketing.bot.modelinterceptor.dto.ClientModelResponse;
import banz.ai.marketing.bot.modelinterceptor.dto.ModelRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ModelCallWrapper {

    private static Logger log = LoggerFactory.getLogger(ModelCallWrapper.class);
    private final ModelClient modelClient;
    private final RabbitTemplate rabbitTemplate;

    public ClientModelResponse wrap(ClientModelRequest modelRequest) {
        log.info("Calling model... %s".formatted(modelRequest));
        var modelResponse = modelClient.makeModelRequest(
                ModelRequest.builder()
                        .text(modelRequest.getText())
                        .isOperator(modelRequest.isOperator())
                        .dialogId(modelRequest.getDialogId())
                        .messages(modelRequest.getMessages())
                .build());
        log.info("Model responded::%s".formatted(modelResponse));

        UUID uuid = UUID.randomUUID();
        var mqMessage = ModelBehaviorDTO.builder()
                .capturedAt(new Date())
                .modelRequest(ModelRequestDTO.builder()
                        .messages(modelRequest.getMessages())
                        .dialogId(modelRequest.getDialogId())
                        .isOperator(modelRequest.isOperator())
                        .text(modelRequest.getText())
                        .build())
                .modelResponse(ModelResponseDTO.builder()
                        .stopTopics(modelResponse.getStopTopics())
                        .offerPurchase(modelResponse.isOfferPurchase())
                        .dialogEvaluation(modelResponse.getDialogEvaluation())
                        .build())
                .build();
        rabbitTemplate.convertAndSend(mqMessage);
        log.info("Message %s was placed in queue".formatted(mqMessage));
        return ClientModelResponse.builder()
                .requestUUID(uuid.toString())
                .offerPurchase(modelResponse.isOfferPurchase())
                .dialogEvaluation(modelResponse.getDialogEvaluation())
                .stopTopics(modelResponse.getStopTopics())
                .build();
    }
}
