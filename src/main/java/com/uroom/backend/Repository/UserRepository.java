package com.uroom.backend.Repository;

import com.uroom.backend.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByCellphone(String cellphone);
    List<User> findByEmail(String email);
}
