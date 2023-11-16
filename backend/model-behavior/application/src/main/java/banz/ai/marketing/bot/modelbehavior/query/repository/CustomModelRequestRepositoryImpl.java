package banz.ai.marketing.bot.modelbehavior.query.repository;

import banz.ai.marketing.bot.modelbehavior.behavior.entity.*;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.AssertionFailure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomModelRequestRepositoryImpl implements CustomModelRequestRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Page<ModelRequest> listPage(Pageable pageable, Map<String, Object> criteria) {
    var filteredProperties = Set.of("performedAt", "requestId");
    JPQLQuery<ModelRequest> query = new JPAQuery<>(entityManager);
    var sorting = pageable.getSort().get()
            .filter(s -> filteredProperties.contains(s.getProperty()))
            .map(s -> {
              if (s.getProperty().equals("performedAt")) {
                if (s.getDirection().equals(Sort.Direction.ASC)) {
                  return QModelRequest.modelRequest.performedAt.asc();
                } else {
                  return QModelRequest.modelRequest.performedAt.desc();
                }
              } else if (s.getProperty().equals("requestId")) {
                if (s.getDirection().equals(Sort.Direction.ASC)) {
                  return QModelRequest.modelRequest.id.asc();
                } else {
                  return QModelRequest.modelRequest.id.desc();
                }
              }
              throw new AssertionFailure("Unreachable...");
            })
            .collect(Collectors.toList());
    var req = query.from(QModelRequest.modelRequest)
            .leftJoin(QModelRequest.modelRequest.messages, QModelRequestMessage.modelRequestMessage)
            .innerJoin(QModelRequest.modelRequest.dialog, QDialog.dialog)
            .leftJoin(QModelRequest.modelRequest.modelResponse, QModelResponse.modelResponse)
            .leftJoin(QModelRequest.modelRequest.modelResponse.stopTopics, QStopTopic.stopTopic)
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .orderBy(sorting.toArray(OrderSpecifier[]::new));
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
            .where(QModelRequest.modelRequest.id.eq(id))
            .fetchOne());
  }
}
