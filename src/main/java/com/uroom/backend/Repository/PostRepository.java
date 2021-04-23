package com.uroom.backend.Repository;

import com.uroom.backend.Models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    //List<Post> findBySomething(String something);
    Post findByAddress(String addres);
    Post findById(int id);
}
