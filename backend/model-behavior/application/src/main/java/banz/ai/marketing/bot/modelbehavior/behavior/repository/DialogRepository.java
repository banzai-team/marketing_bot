package banz.ai.marketing.bot.modelbehavior.behavior.repository;

import banz.ai.marketing.bot.modelbehavior.behavior.entity.Dialog;
import banz.ai.marketing.bot.modelbehavior.query.repository.CustomDialogRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import banz.ai.marketing.bot.modelbehavior.behavior.entity.QDialog;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface DialogRepository extends JpaRepository<Dialog, Long>,
        CustomDialogRepository,
        QuerydslPredicateExecutor<Dialog>,
        QuerydslBinderCustomizer<QDialog> {
    default void customize(QuerydslBindings bindings, QDialog root) {
    }


}
