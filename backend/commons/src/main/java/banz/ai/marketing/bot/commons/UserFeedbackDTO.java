package banz.ai.marketing.bot.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFeedbackDTO {

    long userId;
    long modelResponseId;
    boolean isCorrect;

}
