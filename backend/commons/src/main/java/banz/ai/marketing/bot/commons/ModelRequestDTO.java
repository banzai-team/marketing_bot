package banz.ai.marketing.bot.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ModelRequestDTO {

    private UUID uuid;
    private long dialogId;
    private List<String> messages;
    private boolean isOperator;
    private String text;
}
