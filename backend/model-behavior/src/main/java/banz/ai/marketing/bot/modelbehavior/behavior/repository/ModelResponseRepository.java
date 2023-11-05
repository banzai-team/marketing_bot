package banz.ai.marketing.bot.modelbehavior.behavior.repository;

import banz.ai.marketing.bot.modelbehavior.behavior.entity.ModelResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelResponseRepository extends JpaRepository<ModelResponse, Long> {
}
