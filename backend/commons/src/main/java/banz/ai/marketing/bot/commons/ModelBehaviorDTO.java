package banz.ai.marketing.bot.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelBehaviorDTO {

    private ModelRequestDTO modelRequest;
    private ModelResponseDTO modelResponse;
    private Date capturedAt;
    private String meta;
}
