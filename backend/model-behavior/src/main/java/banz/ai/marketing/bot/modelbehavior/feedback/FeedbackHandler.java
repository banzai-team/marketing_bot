package banz.ai.marketing.bot.modelbehavior.feedback;

import banz.ai.marketing.bot.commons.UserFeedbackDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedbackHandler {

    private static Logger logger = LoggerFactory.getLogger(FeedbackHandler.class);
    private final FeedbackModelClient client;

    public void handleUserFeedback(UserFeedbackDTO userFeedback) {
        // ignore positive feedback
        if (userFeedback.isCorrect()) {
            logger.debug("User's feedback is positive. Ignoring it.");
            return;
        }
        // prepare some data

        //...

        logger.debug("Performing model relearning invocation...");
        client.invokeModelRelearning("Just do it!");
    }
}
