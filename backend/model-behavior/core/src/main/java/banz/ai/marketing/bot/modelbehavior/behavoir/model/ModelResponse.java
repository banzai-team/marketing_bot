package banz.ai.marketing.bot.modelbehavior.behavoir.model;

import java.util.List;

public record ModelResponse(long dialogId, int dialogEvaluation, List<String> stopTopic) {

}
