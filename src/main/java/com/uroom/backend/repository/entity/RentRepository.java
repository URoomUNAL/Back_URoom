package com.uroom.backend.repository.entity;

import com.uroom.backend.models.entity.Post;
import com.uroom.backend.models.entity.Rent;
import com.uroom.backend.models.entity.Rent.Status;
import com.uroom.backend.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentRepository extends JpaRepository<Rent, Integer> {
    List<Rent> findByUser(User user);
    List<Rent> findByPost(Post post);
    Rent findById(int id);
    List<Rent> findByStatus(Status status);
    List<Rent> findByPostAndStatus(Post post, Status status);
}
