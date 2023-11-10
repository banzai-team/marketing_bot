package banz.ai.marketing.bot.modelbehavior.controller;

import banz.ai.marketing.bot.modelbehavior.dto.UserFeedbackDTO;
import banz.ai.marketing.bot.modelbehavior.feedback.FeedbackHandler;
import banz.ai.marketing.bot.modelbehavior.feedback.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/feedback")
@RequiredArgsConstructor
public class UserFeedbackController {

  private final FeedbackService feedbackService;

  @PostMapping
  public ResponseEntity<Void> postModelFeedback(@RequestBody UserFeedbackDTO userFeedbackDTO) {
    feedbackService.postFeedback(userFeedbackDTO);
    return ResponseEntity.status(201).build();
  }
}
