package banz.ai.marketing.bot.modelbehavior.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelRequestByIdQueryDTO {
    private long id;
}
