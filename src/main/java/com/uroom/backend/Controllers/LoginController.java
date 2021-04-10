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

    @PostMapping(path="/login", consumes = "application/json")
    public ResponseEntity<Object> loginUser(@RequestBody User loginUser){
        try{
            System.out.println(loginUser.getName());
            User ans =  userService.selectByEmail(loginUser.getEmail()).iterator().next();
            if(ans.getPassword().equals( loginUser.getPassword() )){
                System.out.println("Usuario valido, buena muchacho");
                ans.setIs_active(true);
                if(userService.update(ans)){
                    return new ResponseEntity<>(ans, HttpStatus.OK);
                }else{
                    return new ResponseEntity<>("No se pudo actualizar", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }else {
                System.out.println("Contraseña incorrecta, Mal :c");
                return new ResponseEntity<>("La contraseña esta mal, Mal :(", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (NoSuchElementException e){
            System.out.println("El correo ingresado no esta registrado");
            return new ResponseEntity<>("El correo ingresado no esta registrado, Mal :(", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-users")
    public Iterable<User> todos(){
        return userService.select();
    }

    @PostMapping(path = "/sign-in", consumes = "application/json")
    public ResponseEntity<Object> signIn(@RequestBody User newUser){
        if(userService.insert(newUser)) { //Es un usuario nuevo
            return new ResponseEntity<>("buena muchacho", HttpStatus.OK);
        }
        return new ResponseEntity<>("Mal :(", HttpStatus.INTERNAL_SERVER_ERROR);
        //List<String> result = Lists.newArrayList(iterable);
        //if(similarUsers)
        //
    }

    @DeleteMapping(path = "/delete-user", consumes = "application/json")
    public ResponseEntity<Object> deleteUser(@RequestBody User deleteUser){
        try{
            User user =  userService.selectByEmail(deleteUser.getEmail()).iterator().next();
            if(userService.delete(user)){
                return new ResponseEntity<>("Melo caramelo", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("No se pudo borrar :c", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (NoSuchElementException e){
            String hola = e.getMessage() + ", no está el muchacho";
            return new ResponseEntity<>(hola, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/deactivate-user", consumes = "application/json")
    public ResponseEntity<Object> deactivateUser(@RequestBody User deactivateUser){
        try{
            User user =  userService.selectByEmail(deactivateUser.getEmail()).iterator().next();
            user.setIs_active(false);
            if(userService.update(user)){
                return new ResponseEntity<>("Melo caramelo", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("No se pudo borrar :c", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (NoSuchElementException e){
            String hola = e.getMessage() + ", no se pudo desactivar";
            return new ResponseEntity<>(hola, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
