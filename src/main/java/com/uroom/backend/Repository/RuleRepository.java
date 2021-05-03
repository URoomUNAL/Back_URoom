package com.uroom.backend.Repository;

import com.uroom.backend.Models.EntitiyModels.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RuleRepository extends JpaRepository<Rule, String> {
        Rule findById(int id);
        Set<Rule> findByNameIn(Set<String> ruleNames);
}
