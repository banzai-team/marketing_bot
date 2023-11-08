package banz.ai.marketing.bot.modelbehavior.behavior;

import java.util.List;

public record ModelResponse(long dialogId, int dialogEvaluation, List<String> stopTopics, boolean offerPurchase) {

}
