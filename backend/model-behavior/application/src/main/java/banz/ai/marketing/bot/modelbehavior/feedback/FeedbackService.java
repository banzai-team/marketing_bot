package banz.ai.marketing.bot.modelbehavior.feedback;

import banz.ai.marketing.bot.commons.mq.UserFeedbackToApplyDTO;
import banz.ai.marketing.bot.modelbehavior.behavior.repository.ModelResponseRepository;
import banz.ai.marketing.bot.modelbehavior.dto.UserFeedbackDTO;
import banz.ai.marketing.bot.modelbehavior.feedback.entity.Feedback;
import banz.ai.marketing.bot.modelbehavior.feedback.entity.FeedbackStatus;
import banz.ai.marketing.bot.modelbehavior.feedback.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class FeedbackService {

  @Value("${queues.feedback-post}")
  private String feedbackPostQueue;
  private final ModelResponseRepository responseRepository;
  private final FeedbackRepository feedbackRepository;

  private final RabbitTemplate rabbitTemplate;

  @Transactional
  public void postFeedback(UserFeedbackDTO userFeedbackDTO) {
    if (responseRepository.existsById(userFeedbackDTO.getModelResponseId())) {
      throw new RuntimeException();
    }
    var response = responseRepository.getReferenceById(userFeedbackDTO.getModelResponseId());
    var feedback = new Feedback();
    feedback.setStatus(FeedbackStatus.PENDING);
    feedback.setCorrect(userFeedbackDTO.isCorrect());
    feedback.setUserId(userFeedbackDTO.getUserId());
    feedback.setModelResponse(response);
    feedback.setCreatedAt(new Date());
    feedbackRepository.save(feedback);

    var mqMessage = UserFeedbackToApplyDTO.builder()
            .feedbackId(feedback.getId())
            .userId(feedback.getUserId())
            .isCorrect(feedback.isCorrect())
            .modelResponseId(userFeedbackDTO.getModelResponseId())
            .build();

    rabbitTemplate.convertAndSend(feedbackPostQueue, mqMessage);
  }
}
