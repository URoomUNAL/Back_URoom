package com.uroom.backend.Services;

import com.uroom.backend.Models.Post;
import com.uroom.backend.Models.User;
import com.uroom.backend.Repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public List<Post> select(){//Hace un select a la base de datos, en este caso los devuelve todos
        return postRepository.findAll();
    }

    public Post insert(Post post){
        try{
            post.setIs_active(true);
            return postRepository.save(post);
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
    public Post update(Post newPost){
        try{
            return postRepository.save(newPost);
        }catch (Exception e){
            return null;
        }
    }

    public Post selectByAddress(String address){
        try {
            return postRepository.findByAddress(address);
        }catch (Exception e){
            return null;
        }
    }

    public Post selectById(int id){
        try {
            return postRepository.findById(id);
        }catch (Exception e){
            return null;
        }
    }


}
