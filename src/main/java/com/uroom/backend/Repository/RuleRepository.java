package com.uroom.backend.Repository;

import com.uroom.backend.Models.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<Rule, String> {
        Rule findById(int id);
}
