package com.uroom.backend.Services;

import com.uroom.backend.Models.EntitiyModels.Interested;
import com.uroom.backend.Models.EntitiyModels.Post;
import com.uroom.backend.Models.EntitiyModels.Visit;
import com.uroom.backend.Repository.InterestedRepository;
import com.uroom.backend.Repository.VisitRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InterestedService {
    final InterestedRepository interestedRepository;

    public InterestedService(InterestedRepository interestedRepository) {
        this.interestedRepository = interestedRepository;
    }

    public List<Interested> select(){
        return interestedRepository.findAll();
    }

    public Interested insert(Interested interested){
        try{
            return interestedRepository.save(interested);
        }catch (Exception e){
            return null;
        }
    }

    public int NumberInterested(Post post, Date begin, Date end){
        return interestedRepository.findByPostAndDateBetween(post,begin,end).size();
    }
}
