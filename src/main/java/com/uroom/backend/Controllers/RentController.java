package com.uroom.backend.Controllers;

import com.uroom.backend.Models.EntitiyModels.Post;
import com.uroom.backend.Models.EntitiyModels.Rent;
import com.uroom.backend.Models.EntitiyModels.User;
import com.uroom.backend.Services.PostService;
import com.uroom.backend.Services.RentService;
import com.uroom.backend.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("test-rent")
    public ResponseEntity<Object> testRent(){
        try {
            Rent myRent = new Rent();
            User user = userService.selectById(17).get(0);
            Post post = postService.selectById(21);
            myRent.setUser(user);
            myRent.setPost(post);
            return new ResponseEntity<Object>(rentService.insert(myRent), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<Object>("Hubo un problema en la prueba, gg", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
