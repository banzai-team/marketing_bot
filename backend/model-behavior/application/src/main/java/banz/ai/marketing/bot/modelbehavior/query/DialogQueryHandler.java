package banz.ai.marketing.bot.modelbehavior.query;

import banz.ai.marketing.bot.modelbehavior.behavior.repository.DialogRepository;
import banz.ai.marketing.bot.modelbehavior.query.dto.DialogDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DialogQueryHandler {

    private final DialogRepository dialogRepository;

    public Page<DialogDTO> query(DialogPageQuery query) {
        return dialogRepository.findAll(query.pageable).map(DialogDTO::project);
    }

}
