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


    @Query(
    value = "select * from post where ST_Distance_Sphere( ST_GeomFromText( CONCAT('POINT(',latitude,' ', longitude,')'), 4326, 'axis-order=lat-long'), ST_GeomFromText('POINT(?1 ?2)', 4326)) <= ?3*1000", nativeQuery = true)
    List<Post> filterByDistance(double latitude, double longitude, double distancia);
}
