package com.debait.debait.rule.repository;

import com.debait.debait.rule.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<Rule, String> {
}
