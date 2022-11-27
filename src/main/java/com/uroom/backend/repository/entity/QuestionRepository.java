package com.uroom.backend.repository.entity;
import com.uroom.backend.models.entity.Post;
import com.uroom.backend.models.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer>{
    List<Question> findByPost(Post post);
    List<Question> findById(int id);
}
