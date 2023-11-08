package banz.ai.marketing.bot.modelbehavior.behavior;

import banz.ai.marketing.bot.commons.ModelRequestDTO;
import banz.ai.marketing.bot.commons.ModelResponseDTO;
import banz.ai.marketing.bot.modelbehavior.behavior.exception.InvalidModelBehaviorException;

import java.util.List;
import java.util.Objects;

public class ModelBehaviorAggregate {

  ModelBehavior root;

  ModelBehaviorAggregate(ModelBehavior root) {
    this.root = root;
  }

  public void setModelResponse(int dialogEvaluation, List<String> stopTopic) {

  }

  public static class Builder {

    private ModelBehavior.Builder rootBuilder;

    private Builder(ModelRequestDTO requestDTO) {

      rootBuilder = ModelBehavior.Builder.forRequest(requestDTO);
    }

    public static Builder forRequest(ModelRequestDTO requestDTO) {
      if (Objects.isNull(requestDTO)) {
        throw new InvalidModelBehaviorException("Request must not be empty");
      }
      return new Builder(requestDTO);
    }

    public Builder withResponse(ModelResponseDTO responseDTO) {
      rootBuilder = rootBuilder.withResponse(responseDTO);
      return this;
    }

    public ModelBehaviorAggregate build() {
      return new ModelBehaviorAggregate(rootBuilder.build());
    }
  }
}
