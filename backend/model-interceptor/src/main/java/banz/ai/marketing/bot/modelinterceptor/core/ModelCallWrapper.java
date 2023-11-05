package banz.ai.marketing.bot.modelinterceptor.core;

import banz.ai.marketing.bot.commons.ModelBehaviorDTO;
import banz.ai.marketing.bot.commons.ModelRequest;
import banz.ai.marketing.bot.commons.ModelResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class ModelCallWrapper {

    private final ModelClient modelClient;
    private final RabbitTemplate rabbitTemplate;

    public ModelResponse wrap(ModelRequest modelRequest) {
        var resp = modelClient.makeModelRequest(modelRequest);
        var modelResponse = resp.getBody();

        var mqMessage = ModelBehaviorDTO.builder()
                .capturedAt(new Date())
                .modelRequest(modelRequest)
                .modelResponse(modelResponse)
                .build();
        rabbitTemplate.convertAndSend(mqMessage);

        return modelResponse;
    }
}
