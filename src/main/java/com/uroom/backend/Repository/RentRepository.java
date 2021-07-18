package com.uroom.backend.Repository;

import com.uroom.backend.Models.EntitiyModels.Post;
import com.uroom.backend.Models.EntitiyModels.Rent;
import com.uroom.backend.Models.EntitiyModels.Rent.Status;
import com.uroom.backend.Models.EntitiyModels.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentRepository extends JpaRepository<Rent, Integer> {
    List<Rent> findByUser(User user);
    List<Rent> findByPost(Post post);
    Rent findById(int id);
    List<Rent> findByStatus(Status status);
    List<Rent> findByPostAndStatus(Post post, Status status);
}
