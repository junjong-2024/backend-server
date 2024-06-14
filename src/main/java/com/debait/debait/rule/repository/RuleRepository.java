package com.debait.debait.rule.repository;

import com.debait.debait.rule.entity.Rule;
import com.debait.debait.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RuleRepository extends JpaRepository<Rule, String> {
    List<Rule> findByUser_id(String userId);

    @Query("SELECT r FROM Rule r WHERE r.id = :ruleId")
    Optional<Rule> findByRule_id(String ruleId);

}
