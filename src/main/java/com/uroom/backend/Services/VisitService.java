package com.uroom.backend.Services;

import com.uroom.backend.Models.EntitiyModels.Image;
import com.uroom.backend.Models.EntitiyModels.Post;
import com.uroom.backend.Models.EntitiyModels.Visit;
import com.uroom.backend.Repository.VisitRepository;
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
        return visitRepository.findByPostAndAndDateBetween(post,begin,end).size();
    }

}
