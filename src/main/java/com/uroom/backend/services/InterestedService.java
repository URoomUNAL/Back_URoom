package com.uroom.backend.services;

import com.uroom.backend.models.entity.Interested;
import com.uroom.backend.models.entity.Post;
import com.uroom.backend.repository.entity.InterestedRepository;
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

    public List<Interested> selectByPost(Post post){
        return interestedRepository.findByPost(post);
    }
}
