package banz.ai.marketing.bot.modelbehavior.behavior;


import banz.ai.marketing.bot.commons.ModelRequestDTO;
import banz.ai.marketing.bot.commons.ModelResponseDTO;
import banz.ai.marketing.bot.modelbehavior.behavior.exception.InvalidModelBehaviorException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

public class ModelBehavior {

  final long dialogId;
  final ModelRequest request;
  ModelResponse response;

  void setResponse(ModelResponse response) {

  }

  private ModelBehavior(long dialogId, ModelRequest request, ModelResponse response) {
    this.dialogId = dialogId;
    this.request = request;
    this.response = response;
  }

  static class Builder {

    private long dialogId;
    private ModelRequest request;
    private ModelResponse response;

    private Builder(ModelRequestDTO modelRequest) {
      this.dialogId = modelRequest.getDialogId();
      if (CollectionUtils.isEmpty(modelRequest.getMessages())) {
        throw new InvalidModelBehaviorException("Model request must contain at least one message");
      }
      request = new ModelRequest(
              dialogId,
              modelRequest.getMessages().stream()
                      .map(s -> new Message(request, s))
                      .collect(Collectors.toList()),
              modelRequest.getText(),
              new Date(),
              modelRequest.isOperator()
      );
    }

    public static Builder forRequest(ModelRequestDTO modelRequest) {
      return new Builder(modelRequest);
    }

    public Builder withResponse(ModelResponseDTO modelResponse) {
      if (Objects.isNull(modelResponse)) {
        throw new InvalidModelBehaviorException("Model behavior request is empty");
      }
      response = new ModelResponse(dialogId,
              modelResponse.getDialogEvaluation(),
              CollectionUtils.isEmpty(modelResponse.getStopTopics()) ? Collections.emptyList() : modelResponse.getStopTopics(),
              modelResponse.isOfferPurchase(),
              0
      );
      return this;
    }

    ModelBehavior build() {
      validate();
      return new ModelBehavior(dialogId, request, response);
    }

    private void validate() throws InvalidModelBehaviorException {
      if (Objects.isNull(request)) {
        throw new InvalidModelBehaviorException("Model behavior request is empty");
      }
    }
  }
}
