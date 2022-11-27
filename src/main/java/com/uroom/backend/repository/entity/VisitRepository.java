package com.uroom.backend.repository.entity;

import com.uroom.backend.models.entity.Post;
import com.uroom.backend.models.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Integer> {
    List<Visit> findByPostAndDateBetween(Post post, Date begin, Date end);

}
