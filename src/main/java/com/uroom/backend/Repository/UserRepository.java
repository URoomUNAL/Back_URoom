package com.uroom.backend.Repository;

import com.uroom.backend.Models.EntitiyModels.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByCellphone(String cellphone);
    List<User> findByEmail(String email);
}
