package com.uroom.backend.services;

import com.uroom.backend.models.entity.Post;
import com.uroom.backend.models.entity.Question;
import com.uroom.backend.repository.entity.QuestionRepository;
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
            System.out.println("Error al insertar pregunta: "+e.getMessage());
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

    public List<Question> sectById(int id){
        try{
            return this.questionRepository.findById(id);
        }catch(Exception e){
            return null;
        }
    }

    public boolean delete(Question question){
        try{
            if(questionRepository.findById(question.getId()).isEmpty()) return false;
            questionRepository.delete(question);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean update(Question question){
        try{
            if(questionRepository.findById(question.getId()).isEmpty()) return false;
            questionRepository.save(question);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
