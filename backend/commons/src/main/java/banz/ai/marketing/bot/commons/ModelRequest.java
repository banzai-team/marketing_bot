package banz.ai.marketing.bot.commons;

import lombok.Data;

import java.util.List;

@Data
public class ModelRequest {

    private long dialogId;
    private List<String> messages;
    private boolean isOperator;
    private String text;
}