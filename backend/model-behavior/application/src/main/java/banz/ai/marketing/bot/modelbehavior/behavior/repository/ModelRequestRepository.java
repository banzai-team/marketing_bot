package banz.ai.marketing.bot.modelbehavior.behavior.repository;

import banz.ai.marketing.bot.modelbehavior.behavior.entity.ModelRequest;
import banz.ai.marketing.bot.modelbehavior.behavior.entity.QDialog;
import banz.ai.marketing.bot.modelbehavior.behavior.entity.QModelRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface ModelRequestRepository extends JpaRepository<ModelRequest, Long>,
        QuerydslPredicateExecutor<ModelRequest>,
        QuerydslBinderCustomizer<QModelRequest> {
    default void customize(QuerydslBindings bindings, QDialog root) {
    }
}
