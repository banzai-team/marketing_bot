package banz.ai.marketing.bot.commons;

import lombok.Data;

@Data
public class UserFeedbackDTO {

    long userId;
    long modelResponseId;
    boolean isCorrect;
}
