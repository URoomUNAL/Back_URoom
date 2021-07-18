package com.uroom.backend.Controllers;

import com.uroom.backend.Models.EntitiyModels.Post;
import com.uroom.backend.Models.EntitiyModels.Rent;
import com.uroom.backend.Models.EntitiyModels.User;
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

@Controller
public class RentController {
    private RentService rentService;
    private UserService userService;
    private PostService postService;

    public RentController(RentService rentService, UserService userService, PostService postService) {
        this.rentService = rentService;
        this.userService = userService;
        this.postService = postService;
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
    public ResponseEntity<Object> unrent_post(@RequestParam(name="id") int post_id){
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
                rented.setUser(user);
                rented.setPost(post);
                rented.setStatus(Rent.Status.ENDED);
                rented.setBegin(LocalDate.now());
                this.rentService.insert(rented);
                return new ResponseEntity<>("Habitación arrendada satisfactoriamente", HttpStatus.OK);
            }
        }
    }

    @GetMapping("test-rent")
    public ResponseEntity<Object> testRent(){
        try {
            Rent myRent = new Rent();
            User user = userService.selectById(21).get(0);
            Post post = postService.selectById(29);
            myRent.setUser(user);
            myRent.setPost(post);
            myRent.setStatus(Rent.Status.RENT);
            myRent.setBegin(LocalDate.now());

            this.rentService.insert(myRent);
            return new ResponseEntity<Object>("buena muchacho", HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<Object>("Hubo un problema en la prueba, gg", HttpStatus.INTERNAL_SERVER_ERROR);
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
