package com.uroom.backend.repository.entity;

import com.uroom.backend.models.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RuleRepository extends JpaRepository<Rule, String> {
        Rule findById(int id);
        Set<Rule> findByNameIn(Set<String> ruleNames);
}
