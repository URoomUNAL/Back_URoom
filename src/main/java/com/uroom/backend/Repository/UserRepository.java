package com.uroom.backend.Repository;

import com.uroom.backend.Models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    @Query(value = "SELECT * FROM user where cellphone like  ?1 ;", nativeQuery = true)
    Iterable<User> findByCellphone(String cellphone);

    @Query(value = "SELECT * FROM user where email like  ?1 ;", nativeQuery = true)
    Iterable<User> findByEmail(String email);
}
