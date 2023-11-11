package banz.ai.marketing.bot.modelbehavior.behavior;

import banz.ai.marketing.bot.commons.ModelBehaviorDTO;
import banz.ai.marketing.bot.modelbehavior.behavior.exception.InvalidModelBehaviorException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BehaviorHandler {

    private static Logger log = LoggerFactory.getLogger(BehaviorHandler.class);
    private final ModelBehaviorDomainRepository repository;

    @Transactional(readOnly = false)
    public void handleBehavior(ModelBehaviorDTO modelBehavior) throws InvalidModelBehaviorException {
        log.info("Saving model behavior...");
        ModelBehaviorAggregate aggregate = ModelBehaviorAggregate.Builder
                .forRequest(modelBehavior.getModelRequest())
                .withResponse(modelBehavior.getModelResponse())
                .build();
        repository.save(aggregate);
        log.info("Aggregate was successfully saved");
    }
}
