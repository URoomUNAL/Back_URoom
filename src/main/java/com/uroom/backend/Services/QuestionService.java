package com.uroom.backend.Services;

import com.uroom.backend.Models.EntitiyModels.Post;
import com.uroom.backend.Models.EntitiyModels.Question;
import com.uroom.backend.Repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository){
        this.questionRepository = questionRepository;
    }

    public Question insert(Question question){
        try{
            return this.questionRepository.save(question);
        }catch(Exception e){
            return null;
        }
    }

    public List<Question> selectByPost(Post post){
        try{
            return this.questionRepository.findByPost(post);
        }catch(Exception e){
            return null;
        }
    }
}
