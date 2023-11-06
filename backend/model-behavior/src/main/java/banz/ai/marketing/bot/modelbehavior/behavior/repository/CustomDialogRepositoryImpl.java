package banz.ai.marketing.bot.modelbehavior.behavior.repository;

import banz.ai.marketing.bot.modelbehavior.behavior.entity.Dialog;
import banz.ai.marketing.bot.modelbehavior.behavior.entity.QDialog;
import banz.ai.marketing.bot.modelbehavior.behavior.entity.QModelRequest;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class CustomDialogRepositoryImpl implements CustomDialogRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Dialog> findAllWithRequests(Pageable pageable) {
        JPQLQuery<Dialog> query = new JPAQuery<>(entityManager);

        var result = query.from(QDialog.dialog)
                .innerJoin(QModelRequest.modelRequest)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }
}
