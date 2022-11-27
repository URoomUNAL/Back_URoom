package com.uroom.backend.repository.entity;

import com.uroom.backend.models.entity.Interested;
import com.uroom.backend.models.entity.InterestedKey;
import com.uroom.backend.models.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface InterestedRepository extends JpaRepository<Interested, InterestedKey> {
    List<Interested> findByPostAndDateBetween(Post post, Date begin, Date end);
    List<Interested> findByPost(Post post);
}
