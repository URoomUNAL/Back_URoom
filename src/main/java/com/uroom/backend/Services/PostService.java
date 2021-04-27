package com.uroom.backend.Services;

import com.uroom.backend.Models.Post;
import com.uroom.backend.Models.Rule;
import com.uroom.backend.Models.User;
import com.uroom.backend.Repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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

    public List<Post> filterAll(int minPrice, int maxPrice, double minScore, com.uroom.backend.Models.Service service, Rule rule) {
        return postRepository.findPostByPriceBetweenAndScoreAfterAndServicesAndRules(minPrice, maxPrice, minScore, service, rule);
    }
    public List<Post> filterAllNoScore(int minPrice, int maxPrice, com.uroom.backend.Models.Service service, Rule rule) {
        return postRepository.findPostByPriceBetweenAndAndServicesAndRules(minPrice, maxPrice, service, rule);
    }

    public List<Post> filterServices(int minPrice, int maxPrice, double minScore, com.uroom.backend.Models.Service service) {
        return postRepository.findPostByPriceBetweenAndScoreAfterAndServices(minPrice, maxPrice, minScore, service);
    }

    public List<Post> filterServicesNoScore(int minPrice, int maxPrice, com.uroom.backend.Models.Service service) {
        return postRepository.findPostByPriceBetweenAndAndServices(minPrice, maxPrice, service);
    }

    public List<Post> filterRules(int minPrice, int maxPrice, double minScore, Rule rule) {
        return postRepository.findPostByPriceBetweenAndScoreAfterAndRules(minPrice, maxPrice, minScore, rule);
    }

    public List<Post> filterRulesNoScore(int minPrice, int maxPrice, Rule rule) {
        return postRepository.findPostByPriceBetweenAndAndRules(minPrice, maxPrice, rule);
    }

    public List<Post> filterBasic(int minPrice, int maxPrice, double minScore) {
        return postRepository.findPostByPriceBetweenAndScoreAfter(minPrice, maxPrice, minScore);
    }

    public List<Post> filterBasicNoScore(int minPrice, int maxPrice) {
        return postRepository.findPostByPriceBetween(minPrice, maxPrice);
    }

    public List<Post> filterDistance(double latitude, double longitude, double distance){
        return postRepository.filterByDistance(latitude, longitude, distance);
    }
}
