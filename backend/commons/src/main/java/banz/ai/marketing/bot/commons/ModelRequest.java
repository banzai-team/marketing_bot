package banz.ai.marketing.bot.commons;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ModelRequest {

    private long dialogId;
    private List<String> messages;
    private boolean isOperator;
    private String text;
}