package com.uroom.backend.Controllers;


import com.uroom.backend.Models.User;
import com.uroom.backend.Services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@CrossOrigin("http://localhost:8080")
@RestController //Es un controlador de tipo REST
public class LoginController {


    private final UserService userService;

    public LoginController(UserService userService){ //Los servicios se pasan como atributos, son globales
        this.userService = userService;
    }
    @RequestMapping("/login")
    public ResponseEntity<Object> Login(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");

        return new ResponseEntity<>(
                "Custom header set", headers, HttpStatus.OK);
    }

    @GetMapping("/getUsers")
    public Iterable<User> todos(){
        return userService.select();
    }

    @PostMapping(path = "/SignIn", consumes = "application/json")
    public ResponseEntity<Object> SignIn(@RequestBody User newUser){
        if(userService.insert(newUser)) { //Es un usuario nuevo
            return new ResponseEntity<>("buena muchacho", HttpStatus.OK);
        }
        return new ResponseEntity<>("Mal :(", HttpStatus.INTERNAL_SERVER_ERROR);
        //List<String> result = Lists.newArrayList(iterable);
        //if(similarUsers)
        //

    }



}
