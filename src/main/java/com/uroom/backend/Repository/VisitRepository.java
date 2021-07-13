package com.uroom.backend.Repository;

import com.uroom.backend.Models.EntitiyModels.Image;
import com.uroom.backend.Models.EntitiyModels.Post;
import com.uroom.backend.Models.EntitiyModels.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Integer> {
    List<Visit> findByPostAndAndDateBetween(Post post, Date begin, Date end);

}
