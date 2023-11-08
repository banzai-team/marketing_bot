package banz.ai.marketing.bot.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelRequestDTO {

    private long dialogId;
    private List<String> messages;
    private boolean isOperator;
    private String text;
}
