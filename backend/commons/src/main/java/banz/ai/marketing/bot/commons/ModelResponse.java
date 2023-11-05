package banz.ai.marketing.bot.commons;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ModelResponse {

    private boolean offerPurchase;
    private int dialogEvaluation;
    private List<String> stopTopics;
}
