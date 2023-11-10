package banz.ai.marketing.bot.modelbehavior.query;

import banz.ai.marketing.bot.commons.ModelRequestDTO;
import banz.ai.marketing.bot.commons.ModelResponseDTO;
import banz.ai.marketing.bot.modelbehavior.behavior.entity.ModelRequestMessage;
import banz.ai.marketing.bot.modelbehavior.behavior.repository.ModelRequestRepository;
import banz.ai.marketing.bot.modelbehavior.dto.UserFeedbackDTO;
import banz.ai.marketing.bot.modelbehavior.query.dto.*;
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
                      .request(ModelRequestListItemDTO.builder()
                              .id(r.getId())
                              .dialogId(r.getDialog().getId())
                              .messages(r.getMessages().stream().map(ModelRequestMessage::getContent).collect(Collectors.toList()))
                              .text(r.getText())
                              .isOperator(r.isOperator())
                              .build()
                      );
              if (Objects.nonNull(r.getModelResponse())) {
                itemBuilder.response(ModelResponseListItemDTO.builder()
                                .id(r.getModelResponse().getId())
                                .offerPurchase(r.getModelResponse().isOfferPurchase())
                                .dialogEvaluation(r.getModelResponse().getDialogEvaluation())
                                .feedbacks(r.getModelResponse().getFeedbacks().stream()
                                        .map(f -> UserFeedbackListItemDTO.builder()
                                                .id(f.getId())
                                                .isCorrect(f.isCorrect())
                                                .userId(f.getUserId())
                                                .build())
                                        .collect(Collectors.toList()))
                                .build())
                        .build();
              }
              return itemBuilder.build();
            }
    );
  }
}
