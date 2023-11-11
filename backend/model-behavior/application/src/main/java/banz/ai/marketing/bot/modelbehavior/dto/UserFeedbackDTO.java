package banz.ai.marketing.bot.modelbehavior.dto;

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
  private long modelResponseId;
  private boolean isCorrect;
}
