package banz.ai.marketing.bot.modelbehavior.query.repository;

import banz.ai.marketing.bot.modelbehavior.behavior.entity.*;
import banz.ai.marketing.bot.modelbehavior.feedback.entity.QFeedback;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class CustomModelRequestRepositoryImpl implements CustomModelRequestRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<ModelRequest> listPage(Pageable pageable, Map<String, Object> criteria) {
        JPQLQuery<ModelRequest> query = new JPAQuery<>(entityManager);

        var req = query.from(QModelRequest.modelRequest)
                .leftJoin(QModelRequest.modelRequest.messages, QModelRequestMessage.modelRequestMessage)
                .innerJoin(QModelRequest.modelRequest.dialog, QDialog.dialog)
                .leftJoin(QModelRequest.modelRequest.modelResponse, QModelResponse.modelResponse)
                .leftJoin(QModelRequest.modelRequest.modelResponse.stopTopics, QStopTopic.stopTopic)
                .leftJoin(QModelResponse.modelResponse.feedbacks, QFeedback.feedback)
                .limit(1000)
                .offset(0);
        if (criteria.containsKey("dialogId")) {
            req.where(QModelRequest.modelRequest.dialog.id.eq((Long) criteria.get("dialogId")));
        }
        var result = req.fetchResults();
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public Optional<ModelRequest> getById(long id) {
        JPQLQuery<ModelRequest> query = new JPAQuery<>(entityManager);

        return Optional.ofNullable(query.from(QModelRequest.modelRequest)
                .leftJoin(QModelRequest.modelRequest.messages, QModelRequestMessage.modelRequestMessage)
                .innerJoin(QModelRequest.modelRequest.dialog, QDialog.dialog)
                .leftJoin(QModelRequest.modelRequest.modelResponse, QModelResponse.modelResponse)
                .leftJoin(QModelRequest.modelRequest.modelResponse.stopTopics, QStopTopic.stopTopic)
                .leftJoin(QModelResponse.modelResponse.feedbacks, QFeedback.feedback)
                .where(QModelRequest.modelRequest.id.eq(id))
                .fetchOne());
    }
}
