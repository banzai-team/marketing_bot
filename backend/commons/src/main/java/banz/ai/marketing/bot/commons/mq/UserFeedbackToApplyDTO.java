package banz.ai.marketing.bot.commons.mq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFeedbackToApplyDTO {

  private long feedbackId;
  private long userId;
  private long modelRequestId;
  private boolean isCorrect;
}
