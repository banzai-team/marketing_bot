package banz.ai.marketing.bot.modelbehavior.controller;

import banz.ai.marketing.bot.modelbehavior.query.ModelRequestQueryHandler;
import banz.ai.marketing.bot.modelbehavior.query.dto.ModelRequestListingItem;
import banz.ai.marketing.bot.modelbehavior.query.dto.ModelRequestListingQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/query")
@RequiredArgsConstructor
public class QueryController {

  private final ModelRequestQueryHandler modelRequestQueryHandler;

  @GetMapping("model-request")
  public ResponseEntity<Page<ModelRequestListingItem>> listModelRequests(@RequestParam("page") int page,
                                                                         @RequestParam("size") int size) {
    return ResponseEntity.ok().body(modelRequestQueryHandler.list(ModelRequestListingQuery.builder()
            .pageable(PageRequest.of(page, size))
            .build()));
  }
}
