package banz.ai.marketing.bot.modelbehavior.behavoir.repository;

import banz.ai.marketing.bot.modelbehavior.behavoir.model.ModelBehaviorAggregate;

public interface ModelBehaviorDomainRepository {

  ModelBehaviorAggregate save(ModelBehaviorAggregate behaviorAggregate);
  ModelBehaviorAggregate getById(long id);
}
