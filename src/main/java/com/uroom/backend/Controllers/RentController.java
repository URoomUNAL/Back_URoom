package com.uroom.backend.Controllers;

import com.uroom.backend.Models.EntitiyModels.Calification;
import com.uroom.backend.Models.EntitiyModels.Post;
import com.uroom.backend.Models.EntitiyModels.Rent;
import com.uroom.backend.Models.EntitiyModels.User;
import com.uroom.backend.Models.ResponseModels.CalificationResponse;
import com.uroom.backend.Models.ResponseModels.PostRentResponse;
import com.uroom.backend.Services.CalificationService;
import com.uroom.backend.Services.PostService;
import com.uroom.backend.Services.RentService;
import com.uroom.backend.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RentController {
    private RentService rentService;
    private UserService userService;
    private PostService postService;
    private CalificationService calificationService;

    public RentController(RentService rentService, UserService userService, PostService postService, CalificationService calificationService) {
        this.rentService = rentService;
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("test-rent")
    public ResponseEntity<Object> testRent(){
        try {
            Rent myRent = new Rent();
            User user = userService.selectById(17).get(0);
            Post post = postService.selectById(21);
            myRent.setUser(user);
            myRent.setPost(post);
            rentService.insert(myRent);
            return new ResponseEntity<Object>(rentService.insert(myRent), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<Object>("Hubo un problema en la prueba, gg", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("get-rated")
    public ResponseEntity<Object> getRatedRooms(){
        try{
            User user = getCurrentUser();
            List<Rent> rents = rentService.selectByUser_id(user.getId());
            List<PostRentResponse> posts = new ArrayList<PostRentResponse>();
            for(Rent rent : rents){
                Post aux = rent.getPost();
                PostRentResponse ratedPost = new PostRentResponse(aux);
                for(Calification calification : calificationService.selectByPost(aux)){
                    if(calification.getUser().getId() == user.getId()){
                        ratedPost.setIs_rated(true);
                        ratedPost.setCalification(new CalificationResponse(calification));
                        break;
                    }
                }
                posts.add(ratedPost);
            }
            return  new ResponseEntity<Object>(posts, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Object>("Hubo un problema buscando las habitaciones calificadas.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public User getCurrentUser(){
        try {
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userService.selectByEmail(principal.getUsername()).iterator().next();
        }catch(Exception e){
            return null;
        }
    }
}
