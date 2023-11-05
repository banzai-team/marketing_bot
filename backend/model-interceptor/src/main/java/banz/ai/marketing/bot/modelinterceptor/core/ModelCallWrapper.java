package banz.ai.marketing.bot.modelinterceptor.core;

import banz.ai.marketing.bot.commons.ModelRequest;
import banz.ai.marketing.bot.commons.ModelResponse;
import banz.ai.marketing.bot.modelinterceptor.dto.MqMessage;
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

        var mqMessage = new MqMessage();
        mqMessage.setMadeAt(new Date());
        mqMessage.setModelRequest(modelRequest);
        mqMessage.setModelResponse(modelResponse);
        rabbitTemplate.convertAndSend(mqMessage);

        return modelResponse;
    }
}
