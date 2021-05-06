package com.uroom.backend.Controllers;

import com.uroom.backend.Models.EntitiyModels.User;
import com.uroom.backend.Models.RequestModels.UserRequest;
import com.uroom.backend.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class CountController {
    private final UserService userService;

    public CountController(UserService userService) {
        this.userService = userService;
    }

    private User mapUser(UserRequest newUser){
        User user = new User();
        user.setAge(newUser.getAge());
        user.setCellphone(newUser.getCellphone());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        user.setName(newUser.getName());
        user.setIs_student(newUser.isIs_student());
        return user;
    }

    @PostMapping("/update-info")
    public ResponseEntity<Object> updateInfo(@RequestBody UserRequest updatedUser){
        try{
            User user = userService.selectById(updatedUser.getId()).iterator().next();
            if(user.isIs_student() != updatedUser.isIs_student()){
                return new ResponseEntity<>("No puede cambiar su rol en la aplicación", HttpStatus.BAD_REQUEST);
            }
            if(user.getEmail() != updatedUser.getEmail()){
                return new ResponseEntity<>("No puede cambiar su correo electrónico", HttpStatus.BAD_REQUEST);
            }
            List<User> selectByCellphone = userService.selectByCellphone(updatedUser.getCellphone());
            if(selectByCellphone.size() > 0){
                return new ResponseEntity<>("El teléfono ingresado ya está registrado", HttpStatus.BAD_REQUEST);
            }
            LoginController loginController = new LoginController(userService);
            loginController.mapUser(user, updatedUser);
            userService.update(user);


        }
        catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>("Algo salió mal", HttpStatus.BAD_REQUEST);
        }

    }
}
