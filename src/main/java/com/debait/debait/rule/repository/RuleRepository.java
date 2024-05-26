package com.debait.debait.rule.repository;

import com.debait.debait.rule.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RuleRepository extends JpaRepository<Rule, String> {
    List<Rule> findByUser_id(String userId);
}
