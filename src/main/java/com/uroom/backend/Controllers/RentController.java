package com.uroom.backend.Controllers;

import com.uroom.backend.Models.EntitiyModels.Calification;
import com.uroom.backend.Models.EntitiyModels.Post;
import com.uroom.backend.Models.EntitiyModels.Rent;
import com.uroom.backend.Models.EntitiyModels.User;
import com.uroom.backend.Models.ResponseModels.CalificationResponse;
import com.uroom.backend.Models.ResponseModels.PostRentResponse;
import com.uroom.backend.Models.ResponseModels.UserResponse;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
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
        this.calificationService = calificationService;
    }

    @PostMapping("rent-post")
    public ResponseEntity<Object> rent_post(@RequestParam(name="post_id") int post_id, @RequestParam(name="student_id") Integer student_id){
        Post post = this.postService.selectById(post_id);
        User authenticaded_user = getCurrentUser();
        if(authenticaded_user == null){
            return new ResponseEntity<>("El usuario no se encuentra autenticado", HttpStatus.BAD_REQUEST);
        }else {
            User user = post.getUser();
            if(user.getId()!=authenticaded_user.getId()){
                return new ResponseEntity<>("El usuario autenticado no corresponde con el propietario", HttpStatus.BAD_REQUEST);
            }
            else{
                try{
                    Rent myRent = new Rent();
                    if(student_id != null){
                        User student = this.userService.selectById(student_id).get(0);
                        myRent.setUser(student);
                    }
                    myRent.setPost(post);
                    myRent.setStatus(Rent.Status.RENT);
                    myRent.setBegin(LocalDate.now());
                    post.setIs_active(false);
                    this.rentService.insert(myRent);
                    return new ResponseEntity<>("Habitación arrendada satisfactoriamente", HttpStatus.OK);
                }catch (Exception e){
                    System.out.println(e);
                    return new ResponseEntity<>("No se encontró el estudiante", HttpStatus.BAD_REQUEST);
                }
            }
        }
    }


    @PostMapping("unrent-post")
    public ResponseEntity<Object> unrent_post(@RequestParam(name="post_id") int post_id){
        System.out.println("Estoy en unrent");
        Post post = this.postService.selectById(post_id);
        User authenticaded_user = getCurrentUser();
        if(authenticaded_user == null){
            return new ResponseEntity<>("El usuario no se encuentra autenticado", HttpStatus.BAD_REQUEST);
        }else {
            User user = post.getUser();
            if(user.getId()!=authenticaded_user.getId()){
                return new ResponseEntity<>("El usuario autenticado no corresponde con el propietario", HttpStatus.BAD_REQUEST);
            }
            else{
                Rent rented = this.rentService.selectByPostAndStatus(post, Rent.Status.RENT).get(0);
                rented.setStatus(Rent.Status.ENDED);
                rented.setEnd(LocalDate.now());
                post.setIs_active(true);
                this.rentService.insert(rented);
                UserResponse userResponse = new UserResponse(rented.getUser());
                userResponse.setActual_rent_id(rented.getId());
                return new ResponseEntity<>(userResponse, HttpStatus.OK);
            }
        }
    }

    @GetMapping("get-rated")
    public ResponseEntity<Object> getRatedRooms(){
        try{
            User user = getCurrentUser();
            List<Rent> rents = rentService.selectByUser(user);
            List<PostRentResponse> posts = new ArrayList<PostRentResponse>();
            for(Rent rent : rents){
                PostRentResponse ratedPost = new PostRentResponse(rent.getPost());
                for(Calification calification : calificationService.selectByPost(rent.getPost())){
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
            return new ResponseEntity<Object>("Hubo un problema buscando las habitaciones rentadas.", HttpStatus.INTERNAL_SERVER_ERROR);
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
