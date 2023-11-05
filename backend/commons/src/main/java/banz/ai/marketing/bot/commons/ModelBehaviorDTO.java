package banz.ai.marketing.bot.commons;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ModelBehaviorDTO {

    private ModelRequest modelRequest;
    private ModelResponse modelResponse;
    private Date capturedAt;
    private String meta;
}
