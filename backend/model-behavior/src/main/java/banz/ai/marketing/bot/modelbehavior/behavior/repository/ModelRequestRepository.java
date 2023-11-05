package banz.ai.marketing.bot.modelbehavior.behavior.repository;

import banz.ai.marketing.bot.modelbehavior.behavior.entity.ModelRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRequestRepository extends JpaRepository<ModelRequest, Long> {
}
