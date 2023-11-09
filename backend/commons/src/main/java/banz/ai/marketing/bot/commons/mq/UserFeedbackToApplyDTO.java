package banz.ai.marketing.bot.commons.mq;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserFeedbackToApplyDTO {

  private long feedbackId;
  private long userId;
  private long modelRequestId;
  private boolean isCorrect;
}
