package com.uroom.backend.services;

import com.uroom.backend.models.entity.Post;
import com.uroom.backend.models.entity.Visit;
import com.uroom.backend.repository.entity.VisitRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class VisitService {
    final VisitRepository visitRepository;

    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public List<Visit> select(){
        return visitRepository.findAll();
    }

    public Visit insert(Visit visit){
        try{
            return visitRepository.save(visit);
        }catch (Exception e){
            return null;
        }
    }

    public int NumberVisits(Post post, Date begin, Date end){
        return visitRepository.findByPostAndDateBetween(post,begin,end).size();
    }

}
