package banz.ai.marketing.bot.modelbehavior.query.dto;

import lombok.*;
import org.springframework.data.domain.Pageable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelRequestListingQuery {

    private Pageable pageable;
}
