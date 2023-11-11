package banz.ai.marketing.bot.modelbehavior.query.repository;

import banz.ai.marketing.bot.modelbehavior.behavior.entity.ModelRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CustomModelRequestRepository {

  Page<ModelRequest> listPage(Pageable pageable, Map<String, Object> criteria);

  Optional<ModelRequest> getById(long id);
}
