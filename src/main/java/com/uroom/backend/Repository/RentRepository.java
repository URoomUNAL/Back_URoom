package com.uroom.backend.Repository;

import com.uroom.backend.Models.EntitiyModels.Rent;
import com.uroom.backend.Models.EntitiyModels.Rent.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentRepository extends JpaRepository<Rent, Integer> {
    List<Rent> findByUser_id(int user_id);
    List<Rent> findByPost_id(int post_id);
    Rent findById(int id);
    List<Rent> findByStatus(Status status);
}
