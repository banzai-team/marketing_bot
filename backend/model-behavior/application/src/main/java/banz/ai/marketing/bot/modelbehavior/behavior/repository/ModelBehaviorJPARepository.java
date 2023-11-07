package banz.ai.marketing.bot.modelbehavior.behavior.repository;

import banz.ai.marketing.bot.modelbehavior.behavoir.model.ModelBehaviorAggregate;
import banz.ai.marketing.bot.modelbehavior.behavoir.repository.ModelBehaviorDomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ModelBehaviorJPARepository implements ModelBehaviorDomainRepository {

  private final DialogRepository dialogRepository;
  private final ModelRequestRepository requestRepository;
  private final ModelResponseRepository responseRepository;

  @Override
  public ModelBehaviorAggregate save(ModelBehaviorAggregate behaviorAggregate) {
    return null;
  }

  @Override
  public ModelBehaviorAggregate getById(long id) {
    return null;
  }
}
