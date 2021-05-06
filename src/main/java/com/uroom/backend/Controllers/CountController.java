package com.uroom.backend.Controllers;

import com.uroom.backend.Models.EntitiyModels.User;
import com.uroom.backend.Models.RequestModels.UserRequest;
import com.uroom.backend.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CountController {
    private final UserService userService;

    public CountController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("update-info")
    public ResponseEntity<Object> updateInfo(@RequestBody UserRequest updatedUser){
        try{
            User user = userService.selectById(updatedUser.getId()).iterator().next();
            if(user.isIs_student() != updatedUser.isIs_student()){
                return new ResponseEntity<>("No puede cambiar su rol en la aplicación", HttpStatus.BAD_REQUEST);
            }
            if(!user.getEmail().equals(updatedUser.getEmail())){
                return new ResponseEntity<>("No puede cambiar su correo electrónico", HttpStatus.BAD_REQUEST);
            }
            List<User> selectByCellphone = userService.selectByCellphone(updatedUser.getCellphone());
            if(selectByCellphone.size() > 0 && !user.getCellphone().equals(updatedUser.getCellphone())){
                return new ResponseEntity<>("El teléfono ingresado ya está registrado", HttpStatus.BAD_REQUEST);
            }
            LoginController loginController = new LoginController(userService);
            loginController.mapUser(user, updatedUser);
            if(userService.update(user)){
                return new ResponseEntity<>("Actualizado correcta", HttpStatus.BAD_REQUEST);
            }
            else{
                return new ResponseEntity<>("No se pudo actualizar", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.BAD_REQUEST);
        }

    }
}
