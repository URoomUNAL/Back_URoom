package com.uroom.backend.Repository;

import com.uroom.backend.Models.EntitiyModels.Interested;
import com.uroom.backend.Models.EntitiyModels.InterestedKey;
import com.uroom.backend.Models.EntitiyModels.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface InterestedRepository extends JpaRepository<Interested, InterestedKey> {
    List<Interested> findByPostAndDateBetween(Post post, Date begin, Date end);
}
