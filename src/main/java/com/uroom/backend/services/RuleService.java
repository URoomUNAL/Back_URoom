package com.uroom.backend.services;

import org.springframework.stereotype.Service;
import com.uroom.backend.repository.entity.RuleRepository;
import com.uroom.backend.models.entity.Rule;
import java.util.List;
import java.util.Set;

@Service
public class RuleService {
    final RuleRepository ruleRepository;

    public RuleService(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
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

    public List<Rule> select() {
        return ruleRepository.findAll();
    }
    public Set<Rule> selectBySetNames(Set<String> ruleNames){
        return ruleNames == null ? null : ruleRepository.findByNameIn(ruleNames);
    }
}
