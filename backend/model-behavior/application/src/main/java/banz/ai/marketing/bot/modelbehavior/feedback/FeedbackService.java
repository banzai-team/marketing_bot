package banz.ai.marketing.bot.modelbehavior.feedback;

import banz.ai.marketing.bot.modelbehavior.behavior.repository.ModelResponseRepository;
import banz.ai.marketing.bot.modelbehavior.dto.UserFeedbackDTO;
import banz.ai.marketing.bot.modelbehavior.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final ModelResponseRepository responseRepository;

    @Transactional
    public void postFeedback(UserFeedbackDTO userFeedbackDTO) {
        if (!responseRepository.existsById(userFeedbackDTO.getModelResponseId())) {
            throw new NotFoundException("Specified response was not found");
        }
        var response = responseRepository.getReferenceById(userFeedbackDTO.getModelResponseId());
        if (userFeedbackDTO.isCorrect()) {
            response.incrementFeedback();
        } else {
            response.decrementFeedback();
        }
        responseRepository.save(response);
    }
}
