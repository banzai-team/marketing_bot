package banz.ai.marketing.bot.modelbehavior.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelResponseListItemDTO {

    private UUID id;
    private boolean offerPurchase;
    private float dialogEvaluation;
    private List<String> stopTopics;
    private int feedback;
}
