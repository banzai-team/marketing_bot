package banz.ai.marketing.bot.modelbehavior.feedback.repository;

import banz.ai.marketing.bot.modelbehavior.feedback.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
