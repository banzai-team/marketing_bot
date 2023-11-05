package banz.ai.marketing.bot.commons;

import lombok.Data;

import java.util.Date;

@Data
public class ModelBehaviorDTO {

    private ModelRequest modelRequest;
    private ModelResponse modelResponse;
    private Date capturedAt;
    private String meta;
}
