package banz.ai.marketing.bot.modelbehavior.behavior;

import banz.ai.marketing.bot.modelbehavior.behavior.ModelBehaviorAggregate;

public interface ModelBehaviorDomainRepository {

  void save(ModelBehaviorAggregate behaviorAggregate);
  ModelBehaviorAggregate getById(long id);
}
