package banz.ai.marketing.bot.modelinterceptor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientModelResponse {

  private String requestUUID;
  private boolean offerPurchase;
  private float dialogEvaluation;
  private List<String> stopTopics;
}
