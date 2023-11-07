package banz.ai.marketing.bot.modelbehavior.behavoir.model;

import banz.ai.marketing.bot.modelbehavior.behavoir.exception.InvalidModelBehaviorException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ModelRequest {
  final long dialogId;
  final List<Message> messages;
  final String text;
  private ModelResponse response;

  public void setResponse(ModelResponse response) {
    if (Objects.isNull(response)) {
      throw new InvalidModelBehaviorException();
    }
    this.response = response;
  }
}
