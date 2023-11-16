package banz.ai.marketing.bot.modelbehavior.behavior.repository;

import banz.ai.marketing.bot.modelbehavior.behavior.entity.ModelRequest;
import banz.ai.marketing.bot.modelbehavior.behavior.entity.QModelRequest;
import banz.ai.marketing.bot.modelbehavior.query.repository.CustomModelRequestRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.UUID;

public interface ModelRequestRepository extends JpaRepository<ModelRequest, UUID>,
        QuerydslPredicateExecutor<ModelRequest>,
        QuerydslBinderCustomizer<QModelRequest>,
        CustomModelRequestRepository {
    default void customize(QuerydslBindings bindings, QModelRequest root) {
    }
}
