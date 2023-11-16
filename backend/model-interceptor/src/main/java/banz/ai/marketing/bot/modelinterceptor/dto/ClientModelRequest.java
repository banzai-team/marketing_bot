package banz.ai.marketing.bot.modelinterceptor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientModelRequest {

  private long dialogId;
  private List<String> messages;
  private boolean isOperator;
  private String text;
}
