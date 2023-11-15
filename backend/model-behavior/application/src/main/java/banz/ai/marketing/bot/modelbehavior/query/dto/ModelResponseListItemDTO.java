package banz.ai.marketing.bot.modelbehavior.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelResponseListItemDTO {

    private long id;
    private boolean offerPurchase;
    private float dialogEvaluation;
    private List<String> stopTopics;
    private int feedback;
}
