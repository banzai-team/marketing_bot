package banz.ai.marketing.bot.userfeedback.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFeedbackDTO {

  private long userId;
  private long modelRequestId;
  private boolean isCorrect;
}
