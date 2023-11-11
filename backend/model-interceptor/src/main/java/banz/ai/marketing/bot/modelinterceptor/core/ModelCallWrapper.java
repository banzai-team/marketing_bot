package banz.ai.marketing.bot.modelinterceptor.core;

import banz.ai.marketing.bot.commons.ModelBehaviorDTO;
import banz.ai.marketing.bot.commons.ModelRequestDTO;
import banz.ai.marketing.bot.commons.ModelResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class ModelCallWrapper {

    private static Logger log = LoggerFactory.getLogger(ModelCallWrapper.class);
    private final ModelClient modelClient;
    private final RabbitTemplate rabbitTemplate;

    public ModelResponseDTO wrap(ModelRequestDTO modelRequest) {
        log.info("Calling model... %s".formatted(modelRequest));
        var resp = modelClient.makeModelRequest(modelRequest);
        var modelResponse = resp.getBody();
        log.info("Model responded::%s".formatted(modelResponse));

        var mqMessage = ModelBehaviorDTO.builder()
                .capturedAt(new Date())
                .modelRequest(modelRequest)
                .modelResponse(modelResponse)
                .build();
        rabbitTemplate.convertAndSend(mqMessage);
        log.info("Message %s was placed in queue".formatted(mqMessage));
        return modelResponse;
    }
}
