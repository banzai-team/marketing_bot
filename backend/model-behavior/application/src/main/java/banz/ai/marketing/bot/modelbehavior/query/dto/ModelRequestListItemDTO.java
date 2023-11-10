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
public class ModelRequestListItemDTO {

    private long id;
    private long dialogId;
    private List<String> messages;
    private boolean isOperator;
    private String text;
}
