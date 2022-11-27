package com.uroom.backend.repository.entity;

import com.uroom.backend.models.entity.Calification;
import com.uroom.backend.models.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalificationRepository extends JpaRepository<Calification, Integer> {
    List<Calification> findByPost(Post post);
}
