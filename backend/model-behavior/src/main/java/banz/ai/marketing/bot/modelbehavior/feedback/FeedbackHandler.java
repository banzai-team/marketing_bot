package banz.ai.marketing.bot.modelbehavior.feedback;

import banz.ai.marketing.bot.commons.UserFeedbackDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedbackHandler {

    private final FeedbackModelClient client;

    public void invokeModelRelearning(UserFeedbackDTO userFeedback) {
        // prepare some data

        //...
        if (userFeedback.isCorrect()) {
            return;
        }
        client.invokeModelRelearning("Just do it!");
    }
}
