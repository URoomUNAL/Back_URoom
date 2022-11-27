package com.uroom.backend.services;

import com.uroom.backend.models.entity.Post;
import com.uroom.backend.models.entity.Rule;
import com.uroom.backend.models.entity.User;
import com.uroom.backend.repository.entity.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> select() {//Hace un select a la base de datos, en este caso los devuelve todos
        return postRepository.findAll();
    }

    public List<Post> selectActivePosts(){
        return postRepository.filterActives();
    }

    public List<Post> selectMyPosts(User user){
        return postRepository.findByUser(user);
    }
    public Post insert(Post post) {
        try {
            post.setIs_active(true);
            return postRepository.save(post);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Post update(Post newPost) {
        try {
            return postRepository.save(newPost);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public List<Post> selectByAddress(String address) {
        System.out.println(address);
        try {
            return postRepository.findPostsByAddress(address);
        } catch (Exception e) {
            System.out.println("uy ojito");
            return null;
        }
    }

    public Post selectById(int id) {
        try {
            return postRepository.findById(id);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean delete(Post post) {
        try {
            postRepository.delete(post);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public List<Post> filterAll(int minPrice, int maxPrice, double minScore, com.uroom.backend.models.entity.Service service, Rule rule) {
        return postRepository.findPostByPriceBetweenAndScoreBetweenAndServicesAndRules(minPrice, maxPrice, minScore, 5, service, rule);
    }
    public List<Post> filterAllNoScore(int minPrice, int maxPrice, com.uroom.backend.models.entity.Service service, Rule rule) {
        return postRepository.findPostByPriceBetweenAndAndServicesAndRules(minPrice, maxPrice, service, rule);
    }

    public List<Post> filterServices(int minPrice, int maxPrice, double minScore, com.uroom.backend.models.entity.Service service) {
        return postRepository.findPostByPriceBetweenAndScoreBetweenAndServices(minPrice, maxPrice, minScore, 5,  service);
    }

    public List<Post> filterServicesNoScore(int minPrice, int maxPrice, com.uroom.backend.models.entity.Service service) {
        return postRepository.findPostByPriceBetweenAndAndServices(minPrice, maxPrice, service);
    }

    public List<Post> filterRules(int minPrice, int maxPrice, double minScore, Rule rule) {
        return postRepository.findPostByPriceBetweenAndScoreBetweenAndRules(minPrice, maxPrice, minScore,5,  rule);
    }

    public List<Post> filterRulesNoScore(int minPrice, int maxPrice, Rule rule) {
        return postRepository.findPostByPriceBetweenAndAndRules(minPrice, maxPrice, rule);
    }

    public List<Post> filterBasic(int minPrice, int maxPrice, double minScore) {
        return postRepository.findPostByPriceBetweenAndScoreBetween(minPrice, maxPrice, minScore, 5);
    }

    public List<Post> filterBasicNoScore(int minPrice, int maxPrice) {
        return postRepository.findPostByPriceBetween(minPrice, maxPrice);
    }

    public List<Post> filterDistance(double latitude, double longitude, double distance){
        return postRepository.filterByDistance(latitude, longitude, distance);
    }
}
