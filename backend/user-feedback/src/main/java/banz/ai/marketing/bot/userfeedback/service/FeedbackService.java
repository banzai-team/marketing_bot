package banz.ai.marketing.bot.userfeedback.service;

import banz.ai.marketing.bot.commons.mq.UserFeedbackApplyResultDTO;
import banz.ai.marketing.bot.commons.mq.UserFeedbackToApplyDTO;
import banz.ai.marketing.bot.userfeedback.dto.UserFeedbackDTO;
import banz.ai.marketing.bot.userfeedback.entity.Feedback;
import banz.ai.marketing.bot.userfeedback.entity.FeedbackStatus;
import banz.ai.marketing.bot.userfeedback.exception.FeedbackNotFoundException;
import banz.ai.marketing.bot.userfeedback.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedbackService {

  private final FeedbackRepository feedbackRepository;
  private final RabbitTemplate rabbitTemplate;

  @Transactional
  public void postFeedback(UserFeedbackDTO userFeedbackDTO) {
    var feedback = new Feedback();
    feedback.setStatus(FeedbackStatus.PENDING);
    feedback.setCorrect(userFeedbackDTO.isCorrect());
    feedback.setUserId(userFeedbackDTO.getUserId());
    feedback.setModelRequestId(userFeedbackDTO.getModelRequestId());
    feedback.setCreatedAt(new Date());
    feedbackRepository.save(feedback);

    var mqMessage = UserFeedbackToApplyDTO.builder()
            .feedbackId(feedback.getId())
            .userId(feedback.getUserId())
            .isCorrect(feedback.isCorrect())
            .modelRequestId(feedback.getModelRequestId())
            .build();

    rabbitTemplate.convertAndSend(mqMessage);
  }

  @Transactional
  public void handleFeedbackApplied(UserFeedbackApplyResultDTO result) {
    if (!feedbackRepository.existsById(result.getFeedbackId())) {
      throw new FeedbackNotFoundException(result.getFeedbackId());
    }
    // TODO handle errors
    var feedback = feedbackRepository.getReferenceById(result.getFeedbackId());
    feedback.setAppliedAt(result.getAppliedAt());
    feedback.setStatus(FeedbackStatus.APPLIED);
    feedbackRepository.save(feedback);
  }
}
