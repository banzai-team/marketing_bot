package banz.ai.marketing.bot.modelbehavior.behavoir.model;

import banz.ai.marketing.bot.commons.ModelRequestDTO;
import banz.ai.marketing.bot.commons.ModelResponseDTO;
import banz.ai.marketing.bot.modelbehavior.behavoir.exception.InvalidModelBehaviorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ModelBehaviorAggregateTest {

  @Test
  void shouldThrowInvalidModelBehaviorExceptionBecauseRequestIsEmpty() {
    var exception = assertThrows(InvalidModelBehaviorException.class, () -> {
      ModelBehaviorAggregate.Builder.forRequest(null).build();
    });
    assertTrue(exception.getMessage().toLowerCase().contains("request must not be empty"));
  }

  @Test
  void shouldThrowInvalidModelBehaviorExceptionBecauseMessageListIsEmpty() {
    var exception = assertThrows(InvalidModelBehaviorException.class, () -> {
      ModelBehaviorAggregate.Builder.forRequest(ModelRequestDTO
                      .builder()
                      .dialogId(1L)
                      .isOperator(true)
                      .build())
              .withResponse(ModelResponseDTO
                      .builder()
                      .build());
    });
    assertTrue(exception.getMessage().toLowerCase().contains("at least one message"));
  }
}
