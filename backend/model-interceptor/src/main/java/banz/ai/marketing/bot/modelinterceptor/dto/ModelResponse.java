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
public class ModelResponse {
  private boolean offerPurchase;
  private float dialogEvaluation;
  private List<String> stopTopics;

  @JsonProperty("offer_confidence")
  public boolean isOfferPurchase() {
    return offerPurchase;
  }
  @JsonProperty("offer_confidence")
  public void setOfferPurchase(boolean offerPurchase) {
    this.offerPurchase = offerPurchase;
  }

  @JsonProperty("sentimemt_loggit")
  public float getDialogEvaluation() {
    return dialogEvaluation;
  }

  @JsonProperty("sentimemt_loggit")
  public void setDialogEvaluation(float dialogEvaluation) {
    this.dialogEvaluation = dialogEvaluation;
  }

  @JsonProperty("stop_topics")
  public List<String> getStopTopics() {
    return stopTopics;
  }

  @JsonProperty("stop_topics")
  public void setStopTopics(List<String> stopTopics) {
    this.stopTopics = stopTopics;
  }
}
