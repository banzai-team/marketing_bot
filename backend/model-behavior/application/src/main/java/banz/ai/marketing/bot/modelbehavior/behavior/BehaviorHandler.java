package banz.ai.marketing.bot.modelbehavior.behavior;

import banz.ai.marketing.bot.commons.ModelBehaviorDTO;
import banz.ai.marketing.bot.modelbehavior.behavior.exception.InvalidModelBehaviorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BehaviorHandler {

    private final ModelBehaviorDomainRepository repository;

    @Transactional(readOnly = false)
    public void handleBehavior(ModelBehaviorDTO modelBehavior) throws InvalidModelBehaviorException {
        ModelBehaviorAggregate aggregate = ModelBehaviorAggregate.Builder
                .forRequest(modelBehavior.getModelRequest())
                .withResponse(modelBehavior.getModelResponse())
                .build();
        repository.save(aggregate);
    }
}
