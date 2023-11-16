package banz.ai.marketing.bot.modelbehavior.query;

import banz.ai.marketing.bot.modelbehavior.behavior.entity.ModelRequest;
import banz.ai.marketing.bot.modelbehavior.behavior.entity.ModelRequestMessage;
import banz.ai.marketing.bot.modelbehavior.behavior.repository.ModelRequestRepository;
import banz.ai.marketing.bot.modelbehavior.query.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ModelRequestQueryHandler {

    private final ModelRequestRepository modelRequestRepository;

    public Page<ModelRequest> list(ModelRequestListingQuery query) {
        return modelRequestRepository.listPage(query.getPageable(), query.getCriteria());
    }

    public Optional<ModelRequest> getById(ModelRequestByIdQueryDTO query) {
        return modelRequestRepository.getByUUID(query.getId());
    }
}
