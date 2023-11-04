package banz.ai.marketing.bot.commons;

import lombok.Data;

import java.util.List;

@Data
public class ModelResponse {

    private boolean offerPurchase;
    private int dialogPositivity;
    private List<String> stopThemes;
}
