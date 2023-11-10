package banz.ai.marketing.bot.modelbehavior.query.dto;

import banz.ai.marketing.bot.commons.ModelRequestDTO;
import banz.ai.marketing.bot.commons.ModelResponseDTO;
import banz.ai.marketing.bot.modelbehavior.dto.UserFeedbackDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelRequestListingItem {

  private ModelRequestListItemDTO request;
  private ModelResponseListItemDTO response;
}
