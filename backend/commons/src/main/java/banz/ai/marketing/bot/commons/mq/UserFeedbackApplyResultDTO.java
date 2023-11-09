package banz.ai.marketing.bot.commons.mq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFeedbackApplyResultDTO {

  private long feedbackId;
  private Date appliedAt;
  private String message;
}
