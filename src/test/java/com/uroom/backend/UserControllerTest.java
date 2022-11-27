package com.uroom.backend;

//import org.h2.util.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uroom.backend.controllers.UserController;
import com.uroom.backend.models.entity.User;
import com.uroom.backend.models.requests.LoginRequest;
import com.uroom.backend.models.requests.UserRequest;
import com.uroom.backend.models.responses.JwtResponse;
import com.uroom.backend.services.StorageService;
import com.uroom.backend.services.UserService;
import com.uroom.backend.auth.jwt.JwtUtil;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {
    public final HttpStatus SUCCESS_OK = HttpStatus.OK;
    public final HttpStatus SUCCESS_CREATED = HttpStatus.CREATED;
    public final HttpStatus SUCCESS_ACCEPTED = HttpStatus.ACCEPTED;
    public final HttpStatus FAILED_BAD_REQUEST = HttpStatus.BAD_REQUEST;
    public final HttpStatus FAILED_INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;

    @Autowired
    private UserService userService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtils;

    private JwtResponse token;

    @Test
    @Order(1)
    void createUser() throws IOException {
        UserController userController = new UserController(userService, storageService, authenticationManager, jwtUtils);

        String newUser = "{\n" +
                "  \"email\": \"test@test.edu.coo\",\n" +
                "  \"name\": \"Usuario de Prueba\",\n" +
                "  \"password\": \"mala\",\n" +
                "  \"cellphone\": \"3002674935\",\n" +
                "  \"age\":35,\n" +
                "  \"is_student\": true\n" +
                "  }";
        UserRequest user = new ObjectMapper().readValue(newUser, UserRequest.class);
        //assertEquals(FAILED_BAD_REQUEST, userController.signUp(user).getStatusCode());

        //Succesful sign up
        newUser = "{\n" +
                "  \"email\": \"test@test.edu.coo\",\n" +
                "  \"name\": \"Usuario de Prueba\",\n" +
                "  \"password\": \"MICONTRASEÑA1\",\n" +
                "  \"cellphone\": \"3002674935\",\n" +
                "  \"age\":35,\n" +
                "  \"is_student\": true\n" +
                "  }";

        user = new ObjectMapper().readValue(newUser, UserRequest.class);
        //assertEquals(SUCCESS_CREATED, userController.signUp(user).getStatusCode());

        //POSSIBLE DUPLICATED USER
        newUser = "{\n" +
                "  \"email\": \"test@test.edu.coo\",\n" +
                "  \"name\": \"Usuario de Prueba\",\n" +
                "  \"password\": \"MICONTRASEÑA1\",\n" +
                "  \"cellphone\": \"3002674935\",\n" +
                "  \"age\":35,\n" +
                "  \"is_student\": true\n" +
                "  }";
        user = new ObjectMapper().readValue(newUser, UserRequest.class);
        //assertEquals(FAILED_INTERNAL_SERVER_ERROR, userController.signUp(user).getStatusCode());
    }

    @Test
    @Order(2)
    void loginUser() throws IOException {
        UserController userController = new UserController(userService, storageService, authenticationManager, jwtUtils);
        //Invalid email
        String loginUser = "{\n" +
                "  \"email\": \"test@test.edu.cooo\",\n" +
                "  \"password\": \"MICONTRASEÑA1\"\n" +
                "  }";
        LoginRequest user = new ObjectMapper().readValue( loginUser, LoginRequest.class);
        //assertEquals(FAILED_BAD_REQUEST, userController.loginUser(user).getStatusCode());

        //Invalid password
        loginUser = "{\n" +
                "  \"email\": \"test@test.edu.coo\",\n" +
                "  \"password\": \"mala\"\n" +
                "  }";
        user = new ObjectMapper().readValue( loginUser, LoginRequest.class);
        //assertEquals(FAILED_BAD_REQUEST, userController.loginUser(user).getStatusCode());

        //Succes login
        loginUser = "{\n" +
                "  \"email\": \"test@test.edu.coo\",\n" +
                "  \"password\": \"MICONTRASEÑA1\"\n" +
                "  }";
        user = new ObjectMapper().readValue( loginUser, LoginRequest.class);
        //ResponseEntity response = userController.loginUser(user);
        //this.token = (JwtResponse) response.getBody();
        //assertEquals(SUCCESS_OK, response.getStatusCode());
    }

    @Test
    @Order(3)
    void deactivateUser() throws IOException {
        UserController userController = new UserController(userService, storageService, authenticationManager, jwtUtils);
        //Invalid email
        String updateUser = "{\n" +
                "  \"email\": \"test@test.edu.cooo\"\n" +
                "}";
        User user = new ObjectMapper().readValue(updateUser, User.class);
        //assertEquals(FAILED_BAD_REQUEST, userController.deactivateUser(user).getStatusCode());

        //Succesful deactivate user
        updateUser = "{\n" +
                "  \"email\": \"test@test.edu.coo\"\n" +
                "}";
        user = new ObjectMapper().readValue(updateUser, User.class);
        //assertEquals(SUCCESS_ACCEPTED, userController.deactivateUser(user).getStatusCode());
    }

    @Test
    @Order(4)
    void deleteUser() throws IOException {
        UserController userController = new UserController(userService, storageService, authenticationManager, jwtUtils);

        //Invalid email
        String deleteUser = "{\n" +
                "  \"email\": \"test@test.edu.coooo\"\n" +
                "}";
        User user = new ObjectMapper().readValue(deleteUser, User.class);
        //assertEquals(FAILED_BAD_REQUEST, userController.deleteUser(user).getStatusCode());

        //Succesful delete
        deleteUser = "{\n" +
                "  \"email\": \"test@test.edu.coo\"\n" +
                "}";
        user = new ObjectMapper().readValue(deleteUser, User.class);
        //assertEquals(SUCCESS_ACCEPTED, userController.deleteUser(user).getStatusCode());
    }
/**/
}
