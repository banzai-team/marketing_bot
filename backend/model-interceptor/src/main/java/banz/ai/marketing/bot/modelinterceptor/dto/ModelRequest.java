package banz.ai.marketing.bot.modelinterceptor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelRequest {
  private long dialogId;
  private List<String> messages;
  private boolean isOperator;
  private String text;

  @JsonProperty("id_sequence")
  public long getDialogId() {
    return dialogId;
  }

  @JsonProperty("id_sequence")
  public void setDialogId(long dialogId) {
    this.dialogId = dialogId;
  }

  @JsonProperty("messages")
  public List<String> getMessages() {
    return messages;
  }

  @JsonProperty("messages")
  public void setMessages(List<String> messages) {
    this.messages = messages;
  }

  @JsonProperty("is_operator")
  public boolean isOperator() {
    return isOperator;
  }

  @JsonProperty("is_operator")
  public void setOperator(boolean operator) {
    isOperator = operator;
  }

  @JsonProperty("text")
  public String getText() {
    return text;
  }

  @JsonProperty("text")
  public void setText(String text) {
    this.text = text;
  }
}
