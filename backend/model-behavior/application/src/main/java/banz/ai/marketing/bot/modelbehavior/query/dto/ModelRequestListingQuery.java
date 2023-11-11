package banz.ai.marketing.bot.modelbehavior.query.dto;

import lombok.*;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelRequestListingQuery {

    private Pageable pageable;
    @Builder.Default
    private Map<String, Object> criteria = new HashMap<>();
}
