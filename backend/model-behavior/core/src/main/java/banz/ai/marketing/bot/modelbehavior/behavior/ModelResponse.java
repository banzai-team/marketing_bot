package banz.ai.marketing.bot.modelbehavior.behavior;

import java.util.List;
import java.util.UUID;

public record ModelResponse(UUID id, ModelRequest request, float dialogEvaluation, List<String> stopTopics, boolean offerPurchase, int feedback) {

}
