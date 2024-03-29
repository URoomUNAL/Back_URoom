package com.uroom.backend.Controllers;


import com.uroom.backend.Models.EntitiyModels.User;
import com.uroom.backend.Models.RequestModels.LoginRequest;
import com.uroom.backend.Models.RequestModels.PostRequest;
import com.uroom.backend.Models.RequestModels.UpdateUserRequest;
import com.uroom.backend.Models.ResponseModels.JwtResponse;
import com.uroom.backend.Models.RequestModels.UserRequest;
import com.uroom.backend.Models.ResponseModels.UserResponse;
import com.uroom.backend.Services.AzureStorageService;
import com.uroom.backend.Services.UserService;
import com.uroom.backend.auth.jwt.JwtUtil;
import com.uroom.backend.auth.services.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.io.IOException;
import java.util.Objects;

//@CrossOrigin("http://localhost:8080")
@RestController //Es un controlador de tipo REST
public class UserController {
    //TODO:QUITAR LOS BADSMELLS
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final UserService userService;
    private final AzureStorageService azureStorageService;

    final AuthenticationManager authenticationManager;

    final JwtUtil jwtUtils;

    public UserController(UserService userService, AzureStorageService azureStorageService, AuthenticationManager authenticationManager, JwtUtil jwtUtils){ //Los servicios se pasan como atributos, son globales
        this.userService = userService;
        this.azureStorageService = azureStorageService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }


    @GetMapping(path="/nuevaaa")
    public ResponseEntity<Object> pruebaa(){
        return new ResponseEntity<>(userService.selectByEmail("aaaaaaaaaaa@.edu.co"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path="/log-in", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.selectByEmail(loginRequest.getEmail()).iterator().next();
            if (encoder.matches(loginRequest.getPassword(), user.getPassword())) {
                user.setIs_active(true);
                if (userService.update(user)) {
                    Authentication authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    String jwt = jwtUtils.generateJwtToken(authentication);

                    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                    List<String> roles = userDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList());

                    return ResponseEntity.ok(new JwtResponse(jwt,
                            userDetails.getName(),
                            userDetails.getEmail(),
                            roles));
                } else {
                    //TODO:INGRESAR LOG
                    return new ResponseEntity<>("No fue posible reactivar el usuario, por favor intente nuevamente.", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                //TODO:INGRESAR LOG
                return new ResponseEntity<>("La contraseña es incorrecta, por favor intente nuevamente.", HttpStatus.BAD_REQUEST);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("No se encontró el usuario, por favor intente nuevamente.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-users")
    public Iterable<User> todos(){
        return userService.select();
    }


    private void mapUser(User user, UserRequest newUser){
        user.setAge( newUser.getAge().equals("") ? null : Integer.parseInt(newUser.getAge()));
        if(newUser.getCellphone() != null){
            user.setCellphone(newUser.getCellphone());
        }
        if(newUser.getEmail() != null){
            user.setEmail(newUser.getEmail());
        }
        if(newUser.getName() != null){
            user.setName(newUser.getName());
        }
        if(newUser.isIs_student() != null){
            user.setIs_student(newUser.isIs_student());
        }
        if(newUser.getPassword() != null){
            user.setPassword(newUser.getPassword());
        }
        if(!newUser.getPhoto_file().isEmpty()){
            System.out.println("FOTO RECIBIDA");
            String prefix_img = newUser.getEmail();
            String[] extention = Objects.requireNonNull(newUser.getPhoto_file().getOriginalFilename()).split("\\.");
            System.out.println("Nombre: " + extention[1]);
            String photo = null;
            try {
                photo = azureStorageService.writeBlobFile(newUser.getPhoto_file(),prefix_img + "." + extention[extention.length - 1]);
            } catch (IOException e) {
                System.out.println("No se pudo guardar la foto en Azure");
                e.printStackTrace();
            }
            newUser.setPhoto(photo);
            user.setPhoto(newUser.getPhoto());
            user.setFavorites(newUser.getFavorites());
        }
    }

    @PostMapping(path = "/sign-up")
    public ResponseEntity<Object> signUp(@ModelAttribute UserRequest newUser) throws IOException {
        System.out.println("Imagen: "+(newUser.getPhoto_file().isEmpty()));
        if(newUser.getPassword().length() < 6 || newUser.getPassword().length() > 20){
            return new ResponseEntity<>("La contraseña debe tener un longitud entre 6 y 20.", HttpStatus.BAD_REQUEST);
        }
        if(!newUser.getPassword().matches(".*\\d.*")){
            return new ResponseEntity<>("La contraseña debe tener al menos un número.", HttpStatus.BAD_REQUEST);
        }
        boolean correct_cellphone = newUser.getCellphone().matches("3[0-9]{9}");
        if(!correct_cellphone){
            return new ResponseEntity<>("El número de celular debe comenzar por 3 y tener 10 dígitos.", HttpStatus.BAD_REQUEST);
        }
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        User user = new User();
        mapUser(user, newUser);

        switch (userService.insert(user)) { //Es un usuario nuevo
            case 0:
                //TODO:INGRESAR LOG
                newUser.setPhoto_file(null);
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
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user =  userService.selectByEmail(deleteUser.getEmail()).iterator().next();
            if(principal.getUsername()!=user.getEmail()){
                return new ResponseEntity<>("Usted no tiene permisos para eliminar esta cuenta de usuario", HttpStatus.BAD_REQUEST);
            }
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
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user =  userService.selectByEmail(deactivateUser.getEmail()).iterator().next();
            user.setIs_active(false);
            if(principal.getUsername()!=user.getEmail()){
                return new ResponseEntity<>("Usted no tiene permisos para desactivar esta cuenta de usuario", HttpStatus.BAD_REQUEST);
            }
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

    @PostMapping("update-info")
    public ResponseEntity<Object> updateInfo(@ModelAttribute UserRequest updatedUser){
        try{
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.selectByEmail(updatedUser.getEmail()).iterator().next();

            if(!principal.getUsername().equals(user.getEmail())){
                return new ResponseEntity<>("Usted no tiene permisos para actualizar la información de esta cuenta de usuario", HttpStatus.BAD_REQUEST);
            }
            if(updatedUser.isIs_student() != null && user.isIs_student() != updatedUser.isIs_student()){
                return new ResponseEntity<>("No puede cambiar su rol en la aplicación", HttpStatus.BAD_REQUEST);
            }
            if(!user.getEmail().equals(updatedUser.getEmail())){
                return new ResponseEntity<>("No puede cambiar su correo electrónico", HttpStatus.BAD_REQUEST);
            }
            List<User> selectByCellphone = userService.selectByCellphone(updatedUser.getCellphone());
            if(selectByCellphone.size() > 0 && !user.getCellphone().equals(updatedUser.getCellphone())){
                return new ResponseEntity<>("El teléfono ingresado ya está registrado", HttpStatus.BAD_REQUEST);
            }

            mapUser(user, updatedUser);
            if(userService.update(user)){
                return new ResponseEntity<>("Actualizado correcta", HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("No se pudo actualizar", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch(Exception e){
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/get-user")
    public ResponseEntity<Object> getUser(){
        try{
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.selectByEmail(principal.getUsername()).iterator().next();
            return new ResponseEntity<>(new UserResponse(user), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.BAD_REQUEST);
        }
    }

}
