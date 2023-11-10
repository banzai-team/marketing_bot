package banz.ai.marketing.bot.modelbehavior.query.repository;

import banz.ai.marketing.bot.modelbehavior.behavior.entity.Dialog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomDialogRepository {

    Page<Dialog> findAllWithRequests(Pageable pageable);
}
