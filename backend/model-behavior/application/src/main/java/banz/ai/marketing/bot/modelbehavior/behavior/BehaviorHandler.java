package banz.ai.marketing.bot.modelbehavior.behavior;

import banz.ai.marketing.bot.commons.ModelBehaviorDTO;
import banz.ai.marketing.bot.modelbehavior.behavior.repository.ModelBehaviorJPARepository;
import banz.ai.marketing.bot.modelbehavior.behavoir.exception.InvalidModelBehaviorException;
import banz.ai.marketing.bot.modelbehavior.behavoir.model.ModelBehaviorAggregate;
import banz.ai.marketing.bot.modelbehavior.behavoir.repository.ModelBehaviorDomainRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

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
