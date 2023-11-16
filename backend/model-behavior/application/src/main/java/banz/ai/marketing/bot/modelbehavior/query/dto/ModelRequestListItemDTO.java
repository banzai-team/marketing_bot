package banz.ai.marketing.bot.modelbehavior.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelRequestListItemDTO {

    private UUID id;
    private long dialogId;
    private List<String> messages;
    private boolean isOperator;
    private String text;
    private Date performedAt;
    private ModelResponseListItemDTO response;
}
