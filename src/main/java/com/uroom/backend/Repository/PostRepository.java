package com.uroom.backend.Repository;

import com.uroom.backend.Models.Post;
import com.uroom.backend.Models.Rule;
import com.uroom.backend.Models.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post, Integer> {
    //List<Post> findBySomething(String something);
    Post findByAddress(String addres);
    Post findById(int id);
    List<Post> findPostByPriceBetweenAndScoreAfterAndServicesAndRules(int minPrice, int maxPrice, double minScore, Service service, Rule rule);
    List<Post> findPostByPriceBetweenAndScoreAfterAndServices(int minPrice, int maxPrice, double minScore, Service service);
    List<Post> findPostByPriceBetweenAndScoreAfterAndRules(int minPrice, int maxPrice, double minScore, Rule rule);
    List<Post> findPostByPriceBetweenAndScoreAfter(int minPrice, int maxPrice, double minScore);


    /*@Query(
    "select * from post where ST_Distance_Sphere( ST_GeomFromText( 'POINT(latitude longitude)', 4326, 'axis-order=lat-long'), ST_GeomFromText('POINT(?1 ?2)', 4326)) <= ?3*1000"
    "select * from post where ((latitude - ?1)*110.574)^2 + ((longitude - ?2)*(111.32*COS(latitude*PI()/180))^2")
    List<Post> filterByDistance(double LattitudOrigen,double  LongitudOrigen,double Radio);*/
}
