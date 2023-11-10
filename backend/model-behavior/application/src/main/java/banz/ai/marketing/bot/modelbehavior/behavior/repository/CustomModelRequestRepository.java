package banz.ai.marketing.bot.modelbehavior.behavior.repository;

import banz.ai.marketing.bot.modelbehavior.behavior.entity.ModelRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomModelRequestRepository {

  Page<ModelRequest> listPage(Pageable pageable);
}
