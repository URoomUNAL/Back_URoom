package com.uroom.backend.Controllers;

import com.uroom.backend.Models.Post;
import com.uroom.backend.Services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @GetMapping("get-posts")
    public Iterable<Post> getAll(){
        return postService.select();
    }

    @PostMapping(path = "add-post", consumes = "application/json")
    public ResponseEntity<Object> addPost(@RequestBody Post newPost){
        if(postService.insert(newPost)) return new ResponseEntity<>(newPost, HttpStatus.CREATED);
        return new ResponseEntity<>("Algo salio mal al agregar la nueva publicacion, por favor intente nuevamente.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
