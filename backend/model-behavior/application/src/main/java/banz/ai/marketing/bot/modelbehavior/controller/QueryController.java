package banz.ai.marketing.bot.modelbehavior.controller;

import banz.ai.marketing.bot.commons.ApiError;
import banz.ai.marketing.bot.modelbehavior.behavior.entity.ModelRequest;
import banz.ai.marketing.bot.modelbehavior.behavior.entity.ModelRequestMessage;
import banz.ai.marketing.bot.modelbehavior.exception.NotFoundException;
import banz.ai.marketing.bot.modelbehavior.query.ModelRequestQueryHandler;
import banz.ai.marketing.bot.modelbehavior.query.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/model/query")
@RequiredArgsConstructor
public class QueryController {

    private final ModelRequestQueryHandler modelRequestQueryHandler;

    @GetMapping("model-request")
    public ResponseEntity<Page<ModelRequestListingItem>> listModelRequests(@RequestParam("page") int page,
                                                                           @RequestParam("size") int size,
                                                                           @RequestParam(value = "dialogId", required = false) Long dialogId
    ) {
        var query = ModelRequestListingQuery.builder().pageable(PageRequest.of(page, size));
        if (Objects.nonNull(dialogId)) {
            query.criteria(Map.of("dialogId", dialogId));
        }
        return ResponseEntity.ok().body(
                modelRequestQueryHandler.list(query.build())
                        .map(this::mapToDTO));
    }

    @GetMapping("model-request/{id}")
    public ResponseEntity<ModelRequestListingItem> listModelRequests(@PathVariable("id") long id ) {
        return ResponseEntity.ok()
                .body(modelRequestQueryHandler.getById(ModelRequestByIdQueryDTO.builder().id(id).build())
                .map(this::mapToDTO).orElseThrow());
    }

    private ModelRequestListingItem mapToDTO(ModelRequest r) {
        var reqBuilder = ModelRequestListItemDTO.builder()
                .id(r.getId())
                .dialogId(r.getDialog().getId())
                .messages(r.getMessages().stream().map(ModelRequestMessage::getContent).collect(Collectors.toList()))
                .text(r.getText())
                .isOperator(r.isOperator());
        if (Objects.nonNull(r.getModelResponse())) {
            reqBuilder.response(ModelResponseListItemDTO.builder()
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
        var itemBuilder = ModelRequestListingItem.builder()
                .request(reqBuilder.build());

        return itemBuilder.build();
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleException(NotFoundException exception) {
        return ResponseEntity.status(404).body(ApiError.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("Not Found")
                .timestamp(new Date())
                .build()
        );
    }
}
