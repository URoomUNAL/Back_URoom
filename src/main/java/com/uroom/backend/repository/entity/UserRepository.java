package com.uroom.backend.repository.entity;

import com.uroom.backend.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByEmail(String email);
    List<User> findById(int id);
}
