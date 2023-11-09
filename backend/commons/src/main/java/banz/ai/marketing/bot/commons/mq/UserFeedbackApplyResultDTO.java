package banz.ai.marketing.bot.commons.mq;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserFeedbackApplyResultDTO {

  private long feedbackId;
  private Date appliedAt;
  private String message;
}
