package com.uroom.backend.Repository;

import com.uroom.backend.Models.EntitiyModels.Calification;
import com.uroom.backend.Models.EntitiyModels.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalificationRepository extends JpaRepository<Calification, Integer> {
    List<Calification> findByPost(Post post);
}
