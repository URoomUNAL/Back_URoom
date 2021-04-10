package com.uroom.backend.Controllers;


import com.uroom.backend.Models.User;
import com.uroom.backend.Services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

//@CrossOrigin("http://localhost:8080")
@RestController //Es un controlador de tipo REST
public class LoginController {


    private final UserService userService;

    public LoginController(UserService userService){ //Los servicios se pasan como atributos, son globales
        this.userService = userService;
    }
    /*@RequestMapping("/login")
    public ResponseEntity<Object> Login(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");

        return new ResponseEntity<>(
                "Custom header set", headers, HttpStatus.OK);
    }*/

    @GetMapping(path="/login", consumes = "application/json")
    public ResponseEntity<Object> loginUser(@RequestBody User loginUser){
        try{
            User user =  userService.selectByEmail(loginUser.getEmail()).iterator().next();
            if(user.getPassword().equals( loginUser.getPassword() )){
                System.out.println("Usuario valido, buena muchacho");
                user.setIs_active(true);
                if(userService.update(user)){
                    //TODO:INGRESAR LOG
                    return new ResponseEntity<>(user, HttpStatus.OK);
                }else{
                    //TODO:INGRESAR LOG
                    return new ResponseEntity<>("No se pudo actualizar", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }else {
                //TODO:INGRESAR LOG
                //System.out.println("Contraseña incorrecta, Mal :c");
                return new ResponseEntity<>("La contraseña esta mal, Mal :(", HttpStatus.BAD_REQUEST);
            }
        }catch (NoSuchElementException e){
            //TODO:INGRESAR LOG
            //System.out.println("El correo ingresado no esta registrado");
            return new ResponseEntity<>("El correo ingresado no esta registrado, Mal :(", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-users")
    public Iterable<User> todos(){
        return userService.select();
    }

    @PostMapping(path = "/sign-up", consumes = "application/json")
    public ResponseEntity<Object> signUp(@RequestBody User newUser){
        if(userService.insert(newUser)) { //Es un usuario nuevo
            //TODO:INGRESAR LOG
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }
        //TODO:INGRESAR LOG
        return new ResponseEntity<>("Mal :(", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping(path = "/delete-user", consumes = "application/json")
    public ResponseEntity<Object> deleteUser(@RequestBody User deleteUser){
        try{
            User user =  userService.selectByEmail(deleteUser.getEmail()).iterator().next();
            if(userService.delete(user)){
                //TODO:INGRESAR LOG
                return new ResponseEntity<>("", HttpStatus.ACCEPTED);
            }else{
                //TODO:INGRESAR LOG
                return new ResponseEntity<>("No se pudo borrar :c", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (NoSuchElementException e){
            //TODO:INGRESAR LOG
            String message = e.getMessage() + ", no está el muchacho";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/deactivate-user", consumes = "application/json")
    public ResponseEntity<Object> deactivateUser(@RequestBody User deactivateUser){
        try{
            User user =  userService.selectByEmail(deactivateUser.getEmail()).iterator().next();
            user.setIs_active(false);
            if(userService.update(user)){
                //TODO:INGRESAR LOG
                return new ResponseEntity<>("Melo caramelo", HttpStatus.ACCEPTED);
            }else{
                //TODO:INGRESAR LOG
                return new ResponseEntity<>("No se pudo desctivar :c", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (NoSuchElementException e){
            //TODO:INGRESAR LOG
            String message = e.getMessage() + ", no se pudo desactivar";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

    }
}
