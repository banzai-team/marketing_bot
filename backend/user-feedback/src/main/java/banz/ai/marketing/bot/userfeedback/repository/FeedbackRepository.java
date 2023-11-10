package banz.ai.marketing.bot.userfeedback.repository;

import banz.ai.marketing.bot.userfeedback.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
