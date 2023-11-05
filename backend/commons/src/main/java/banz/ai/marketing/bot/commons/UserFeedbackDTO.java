package banz.ai.marketing.bot.commons;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserFeedbackDTO {

    long userId;
    long modelResponseId;
    boolean isCorrect;
}
