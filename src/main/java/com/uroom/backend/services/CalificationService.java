package com.uroom.backend.services;

import com.uroom.backend.models.entity.Calification;
import com.uroom.backend.models.entity.Post;
import com.uroom.backend.repository.entity.CalificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalificationService {
    final CalificationRepository calificationRepository;

    public CalificationService(CalificationRepository calificationRepository){
        this.calificationRepository = calificationRepository;
    }

    public Calification insert(Calification calification){
        try{
            return calificationRepository.save(calification);
        }catch(Exception e){
            return null;
        }
    }

    public List<Calification> selectByPost(Post post){
        try{
            return calificationRepository.findByPost(post);
        }catch (Exception e){
            return null;
        }
    }
}
