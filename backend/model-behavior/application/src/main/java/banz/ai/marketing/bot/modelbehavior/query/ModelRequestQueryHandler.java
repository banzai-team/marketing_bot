package banz.ai.marketing.bot.modelbehavior.query;

import banz.ai.marketing.bot.commons.ModelRequestDTO;
import banz.ai.marketing.bot.commons.ModelResponseDTO;
import banz.ai.marketing.bot.modelbehavior.behavior.repository.ModelRequestRepository;
import banz.ai.marketing.bot.modelbehavior.dto.UserFeedbackDTO;
import banz.ai.marketing.bot.modelbehavior.query.dto.ModelRequestListingItem;
import banz.ai.marketing.bot.modelbehavior.query.dto.ModelRequestListingQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ModelRequestQueryHandler {

  private final ModelRequestRepository modelRequestRepository;

  public Page<ModelRequestListingItem> list(ModelRequestListingQuery query) {
    var requests = modelRequestRepository.listPage(query.getPageable());
    return requests.map(r -> {
              var itemBuilder = ModelRequestListingItem.builder()
                      .request(ModelRequestDTO.builder()
                              .dialogId(r.getDialog().getId())
                              .text(r.getText())
                              .build()
                      );
              if (Objects.nonNull(r.getModelResponse())) {
                itemBuilder.response(ModelResponseDTO.builder()
                                .offerPurchase(r.getModelResponse().isOfferPurchase())
                                .dialogEvaluation(r.getModelResponse().getDialogEvaluation())
                                .build())
                        .feedback(r.getModelResponse().getFeedbacks().stream()
                                .map(f -> UserFeedbackDTO.builder()
                                        .isCorrect(f.isCorrect())
                                        .userId(f.getUserId())
                                        .build())
                                .collect(Collectors.toList()))
                        .build();
              }
              return itemBuilder.build();
            }
    );
  }
}
