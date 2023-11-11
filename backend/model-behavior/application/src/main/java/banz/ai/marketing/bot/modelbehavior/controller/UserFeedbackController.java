package banz.ai.marketing.bot.modelbehavior.controller;

import banz.ai.marketing.bot.commons.ApiError;
import banz.ai.marketing.bot.modelbehavior.dto.UserFeedbackDTO;
import banz.ai.marketing.bot.modelbehavior.exception.NotFoundException;
import banz.ai.marketing.bot.modelbehavior.feedback.FeedbackHandler;
import banz.ai.marketing.bot.modelbehavior.feedback.FeedbackService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/feedback")
@RequiredArgsConstructor
public class UserFeedbackController {

  private static Logger log = LoggerFactory.getLogger(UserFeedbackController.class);
  private final FeedbackService feedbackService;

  @PostMapping
  public ResponseEntity<Void> postModelFeedback(@RequestBody UserFeedbackDTO userFeedbackDTO) {
    log.info("Feedback was posted::%s".formatted(userFeedbackDTO));
    feedbackService.postFeedback(userFeedbackDTO);
    return ResponseEntity.status(201).build();
  }

  @ExceptionHandler(NotFoundException.class)
  protected ResponseEntity<ApiError> handleException(HttpServletRequest request, NotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
            .body(ApiError.builder()
                    .timestamp(new Date())
                    .path(request.getRequestURI())
                    .code(HttpStatus.NOT_FOUND.value())
                    .message(exception.getMessage())
                    .build()
            );
  }
}
