package banz.ai.marketing.bot.modelbehavior.query;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class DialogPageQuery {
    final Pageable pageable;
}
