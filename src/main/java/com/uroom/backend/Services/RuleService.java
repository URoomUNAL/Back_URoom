package com.uroom.backend.Services;

import org.springframework.stereotype.Service;
import com.uroom.backend.Repository.RuleRepository;
import com.uroom.backend.Models.Rule;
import java.util.List;

@Service
public class RuleService {
    final RuleRepository ruleRepository;

    public RuleService(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    public List<Rule> select() {
        return ruleRepository.findAll();
    }

    public Rule insert(Rule newRule) {
        try {
            return ruleRepository.save(newRule);
        } catch (Exception e) {
            return null;
        }

    }

    public Rule selectById(int id){
        return ruleRepository.findById(id);
    }
}
