package banz.ai.marketing.bot.modelbehavior.behavior;

import banz.ai.marketing.bot.commons.ModelBehaviorDTO;
import banz.ai.marketing.bot.modelbehavior.behavior.entity.*;
import banz.ai.marketing.bot.modelbehavior.behavior.exception.InvalidModelBehaviorException;
import banz.ai.marketing.bot.modelbehavior.behavior.repository.DialogRepository;
import banz.ai.marketing.bot.modelbehavior.behavior.repository.ModelRequestRepository;
import banz.ai.marketing.bot.modelbehavior.behavior.repository.ModelResponseRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.stream.IntStream;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BehaviorHandler {

    private final DialogRepository dialogRepository;
    private final ModelRequestRepository modelRequestRepository;
    private final ModelResponseRepository modelResponseRepository;

    @Transactional(readOnly = false)
    public void handleBehavior(ModelBehaviorDTO modelBehavior) throws InvalidModelBehaviorException {
        validate(modelBehavior);
        var dialogAlreadyExists = dialogRepository.existsById(modelBehavior.getModelRequest().getDialogId());
        Dialog dialog;
        if (dialogAlreadyExists) {
            dialog = dialogRepository.getReferenceById(modelBehavior.getModelRequest().getDialogId());
        } else {
            dialog = new Dialog();
            dialog.setCreatedAt(new Date());
        }
        var modelRequest = new ModelRequest();
        var modelResponse = new ModelResponse();
        modelRequest.setDialog(dialog);
        modelRequest.setText(modelBehavior.getModelRequest().getText());
        modelRequest.setOperator(modelBehavior.getModelRequest().isOperator());
        IntStream.range(0, modelBehavior.getModelRequest().getMessages().size())
                .mapToObj(i -> {
                    var dm = new ModelRequestMessage();
                    dm.setContent(modelBehavior.getModelRequest().getMessages().get(i));
                    dm.setOrdinalNumber(i);
                    return dm;
                })
                .forEach(modelRequest::addMessage);
        if (!CollectionUtils.isEmpty(modelBehavior.getModelResponse().getStopTopics())) {
            modelBehavior.getModelResponse().getStopTopics()
                    .stream()
                    .map(s -> {
                        var stopTopic = new StopTopic();
                        stopTopic.setContent(s);
                        return stopTopic;
                    })
                    .forEach(modelResponse::addStopTopic);
        }
        modelResponse.setOfferPurchase(modelBehavior.getModelResponse().isOfferPurchase());
        modelResponse.setDialogEvaluation(modelBehavior.getModelResponse().getDialogEvaluation());
        modelResponse.setModelRequest(modelRequest);
        modelRequest.setModelResponse(modelResponse);

        dialogRepository.save(dialog);
        modelRequestRepository.save(modelRequest);
        modelResponseRepository.save(modelResponse);
    }

    private void validate(ModelBehaviorDTO modelBehavior) throws InvalidModelBehaviorException {
        if (Objects.isNull(modelBehavior)) {
            throw new InvalidModelBehaviorException("Model behavior message is empty");
        }
        if (Objects.isNull(modelBehavior.getModelRequest())) {
            throw new InvalidModelBehaviorException("Model behavior request is empty");
        }
        if (modelBehavior.getModelRequest().getDialogId() <= 0) {
            throw new InvalidModelBehaviorException("Dialog id must be set or greater than 0");
        }
        if (CollectionUtils.isEmpty(modelBehavior.getModelRequest().getMessages())) {
            throw new InvalidModelBehaviorException("Model request must contain at least one message");
        }
    }

}
