package com.uroom.backend.Controllers;

import com.uroom.backend.Models.Image;
import com.uroom.backend.Models.Post;
import com.uroom.backend.POJOS.PostPOJO;
import com.uroom.backend.Repository.ImageRepository;
import com.uroom.backend.Services.ImageService;
import com.uroom.backend.Services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PostController {
    private final PostService postService;
    private final ImageService imageService;

    public PostController(PostService postService, ImageService imageService){
        this.postService = postService;
        this.imageService = imageService;
    }

    @GetMapping("get-posts")
    public List<Post> getAll(){
        return postService.select();
    }

    @GetMapping("get-images")
    public List<Image> getAllImages(){
        return imageService.select();
    }


    @PostMapping(path = "add-post", consumes = "application/json")
    public ResponseEntity<Object> addPost(@ModelAttribute Post newPost){
        //System.out.println(newPost.getMain_img().getName());

        Post myPost = postService.insert(newPost);
        if(myPost != null){
        /*
            List<Image> images = new ArrayList<>();
            for (Image im : newPost.getImages()){
                im.setPost(myPost);
                images.add(imageService.insert(im));
            }
            myPost.setImages(images);
        */
            return new ResponseEntity<>(myPost, HttpStatus.CREATED);
        }

        return new ResponseEntity<>("jaja no debi aser eso", HttpStatus.OK);
        //return new ResponseEntity<>("Algo salio mal al agregar la nueva publicacion, por favor intente nuevamente.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
