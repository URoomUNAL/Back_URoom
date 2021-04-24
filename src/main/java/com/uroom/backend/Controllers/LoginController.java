package com.uroom.backend.Controllers;


import com.uroom.backend.Models.User;
import com.uroom.backend.Services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

//@CrossOrigin("http://localhost:8080")
@RestController //Es un controlador de tipo REST
public class LoginController {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final UserService userService;

    public LoginController(UserService userService){ //Los servicios se pasan como atributos, son globales
        this.userService = userService;
    }


    @GetMapping(path="/nuevaaa")
    public ResponseEntity<Object> pruebaa(){
        return new ResponseEntity<>(userService.selectByEmail("aaaaaaaaaaa@.edu.co"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path="/log-in", consumes = "application/json")
    public ResponseEntity<Object> loginUser(@RequestBody User loginUser){
        try{
            User user =  userService.selectByEmail(loginUser.getEmail());
            if(encoder.matches(loginUser.getPassword(), user.getPassword()) ){
                System.out.println("Usuario valido, buena muchacho");
                user.setIs_active(true);
                if(userService.update(user)){
                    //TODO:INGRESAR LOG
                    return new ResponseEntity<>(user, HttpStatus.OK);
                }else{
                    //TODO:INGRESAR LOG
                    return new ResponseEntity<>("No fue posible reactivar el usuario, por favor intente nuevamente.", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }else {
                //TODO:INGRESAR LOG
                return new ResponseEntity<>("La contraseña es incorrecta, por favor intente nuevamente.", HttpStatus.BAD_REQUEST);
            }
        }catch (NoSuchElementException e){
            //TODO:INGRESAR LOG
            //System.out.println("El correo ingresado no esta registrado");
            return new ResponseEntity<>("No se encontró el usuario, por favor intente nuevamente.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-users")
    public Iterable<User> todos(){
        return userService.select();
    }

    @PostMapping(path = "/sign-up", consumes = "application/json")
    public ResponseEntity<Object> signUp(@RequestBody User newUser){
        if(newUser.getPassword().length() < 6 || newUser.getPassword().length() > 20){
            return new ResponseEntity<>("La contraseña debe tener un longitud entre 6 y 20.", HttpStatus.BAD_REQUEST);
        }
        if(!newUser.getPassword().matches(".*\\d.*")){
            return new ResponseEntity<>("La contraseña debe tener al menos un numero.", HttpStatus.BAD_REQUEST);
        }
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        switch (userService.insert(newUser)) { //Es un usuario nuevo
            case 0:
                //TODO:INGRESAR LOG
                return new ResponseEntity<>(newUser, HttpStatus.CREATED);
            case 1:
                //TODO:INGRESAR LOG
                return new ResponseEntity<>("Ya existe un usuario con ese teléfono, por favor intente nuevamente.", HttpStatus.INTERNAL_SERVER_ERROR);
            case 2:
                //TODO:INGRESAR LOG
                return new ResponseEntity<>("Ya existe un usuario registrado con ese correo, por favor intente nuevamente.", HttpStatus.INTERNAL_SERVER_ERROR);
            default:
                //TODO:INGRESAR LOG
                return new ResponseEntity<>("Algo salio mal en su registro, por favor intente nuevamente.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/delete-user", consumes = "application/json")
    public ResponseEntity<Object> deleteUser(@RequestBody User deleteUser){
        try{
            User user =  userService.selectByEmail(deleteUser.getEmail());
            if(userService.delete(user)){
                //TODO:INGRESAR LOG
                return new ResponseEntity<>("El usuario fue eliminado correctamente.", HttpStatus.ACCEPTED);
            }else{
                //TODO:INGRESAR LOG
                return new ResponseEntity<>("El usuario no pudo ser eliminado, por favor intente nuevamente.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (NoSuchElementException e){
            //TODO:INGRESAR LOG
            return new ResponseEntity<>("No se encontró el usuario, por favor intente nuevamente.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/deactivate-user", consumes = "application/json")
    public ResponseEntity<Object> deactivateUser(@RequestBody User deactivateUser){
        try{
            User user =  userService.selectByEmail(deactivateUser.getEmail());
            user.setIs_active(false);
            if(userService.update(user)){
                //TODO:INGRESAR LOG
                return new ResponseEntity<>("Su cuenta fue desactivada exitosamente.", HttpStatus.ACCEPTED);
            }else{
                //TODO:INGRESAR LOG
                return new ResponseEntity<>("Su cuenta no pudo ser actualizada, por favor intente nuevamente.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (NoSuchElementException e){
            //TODO:INGRESAR LOG
            return new ResponseEntity<>("No se encontró el usuario, por favor intente nuevamente.", HttpStatus.BAD_REQUEST);
        }
    }

}
