package com.uroom.backend.Repository;

import com.uroom.backend.Models.EntitiyModels.Post;
import com.uroom.backend.Models.EntitiyModels.Rule;
import com.uroom.backend.Models.EntitiyModels.Service;
import com.uroom.backend.Models.EntitiyModels.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    //List<Post> findBySomething(String something);
    List<Post> findPostsByAddress(String addres);
    Post findById(int id);
    List<Post> findPostByPriceBetweenAndScoreBetweenAndServicesAndRules(int minPrice, int maxPrice, double minScore, double maxScore, Service service, Rule rule);
    List<Post> findPostByPriceBetweenAndScoreBetweenAndServices(int minPrice, int maxPrice, double minScore, double maxScore, Service service);
    List<Post> findPostByPriceBetweenAndScoreBetweenAndRules(int minPrice, int maxPrice, double minScore, double maxScore, Rule rule);
    List<Post> findPostByPriceBetweenAndScoreBetween(int minPrice, int maxPrice, double minScore,  double maxScore);
    List<Post> findPostByPriceBetweenAndAndServicesAndRules(int minPrice, int maxPrice, Service service, Rule rule);
    List<Post> findPostByPriceBetweenAndAndServices(int minPrice, int maxPrice, Service service);
    List<Post> findPostByPriceBetweenAndAndRules(int minPrice, int maxPrice, Rule rule);
    List<Post> findPostByPriceBetween(int minPrice, int maxPrice);
    @Query(
            value = "select * from post where is_active = 1", nativeQuery = true)
    List<Post> filterActives();

    List<Post> findByUser(User user);


    @Query(
    value = "select * from post where ST_Distance_Sphere( ST_GeomFromText( CONCAT('POINT(',latitude,' ', longitude,')'), 4326, 'axis-order=lat-long'), ST_GeomFromText(CONCAT('POINT(', ?1, ' ', ?2 ,')'), 4326)) <= ?3 * 1000", nativeQuery = true)
    List<Post> filterByDistance(double latitude, double longitude, double distancia);
}
