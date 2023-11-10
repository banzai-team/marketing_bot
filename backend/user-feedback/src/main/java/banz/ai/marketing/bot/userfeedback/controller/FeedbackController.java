package banz.ai.marketing.bot.userfeedback.controller;

import banz.ai.marketing.bot.userfeedback.dto.UserFeedbackDTO;
import banz.ai.marketing.bot.userfeedback.service.FeedbackService;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-11-04T21:45:58.716213200+07:00[Asia/Novosibirsk]")
@RestController
@RequestMapping("api/feedback")
@RequiredArgsConstructor
public class FeedbackController implements FeedbackApi {

  private final FeedbackService feedbackService;

  @Override
  @PostMapping
  public ResponseEntity<Void> postModelFeedback(@Valid @RequestBody UserFeedbackDTO userFeedbackDTO) {
    feedbackService.postFeedback(userFeedbackDTO);
    return ResponseEntity.status(201).build();
  }
}
