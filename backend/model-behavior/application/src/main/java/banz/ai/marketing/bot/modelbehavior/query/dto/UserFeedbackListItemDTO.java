package banz.ai.marketing.bot.modelbehavior.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFeedbackListItemDTO {

    private long id;
    private long userId;
    private long modelResponseId;
    private boolean isCorrect;
}
