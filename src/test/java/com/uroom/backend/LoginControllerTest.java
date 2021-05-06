package com.uroom.backend;

//import org.h2.util.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uroom.backend.Controllers.LoginController;
import com.uroom.backend.Models.EntitiyModels.User;
import com.uroom.backend.Models.RequestModels.LogInRequest;
import com.uroom.backend.Models.RequestModels.UserRequest;
import com.uroom.backend.Services.UserService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginControllerTest {
    public final HttpStatus SUCCESS_OK = HttpStatus.OK;
    public final HttpStatus SUCCESS_CREATED = HttpStatus.CREATED;
    public final HttpStatus SUCCESS_ACCEPTED = HttpStatus.ACCEPTED;
    public final HttpStatus FAILED_BAD_REQUEST = HttpStatus.BAD_REQUEST;
    public final HttpStatus FAILED_INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;

    @Autowired
    private UserService userService;
/**/
    @Test
    @Order(1)
    void createUser() throws IOException {
        LoginController loginController = new LoginController(userService);
        String newUser = "{\n" +
                "  \"email\": \"test@test.edu.coo\",\n" +
                "  \"name\": \"Usuario de Prueba\",\n" +
                "  \"password\": \"mala\",\n" +
                "  \"cellphone\": \"3002674935\",\n" +
                "  \"age\":35,\n" +
                "  \"is_student\": true\n" +
                "  }";
        UserRequest user = new ObjectMapper().readValue(newUser, UserRequest.class);
        assertEquals(FAILED_BAD_REQUEST, loginController.signUp(user).getStatusCode());

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
        assertEquals(SUCCESS_CREATED, loginController.signUp(user).getStatusCode());

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
        assertEquals(FAILED_INTERNAL_SERVER_ERROR, loginController.signUp(user).getStatusCode());
    }
/**/
/**/
    @Test
    @Order(2)
    void loginUser() throws IOException {
        LoginController loginController = new LoginController(userService);
        //Invalid email
        String loginUser = "{\n" +
                "  \"email\": \"test@test.edu.cooo\",\n" +
                "  \"name\": \"Usuario de Prueba\",\n" +
                "  \"password\": \"MICONTRASEÑA1\"\n" +
                "  }";
        LogInRequest user = new ObjectMapper().readValue( loginUser, LogInRequest.class);
        assertEquals(FAILED_BAD_REQUEST, loginController.loginUser(user).getStatusCode());

        //Invalid password
        loginUser = "{\n" +
                "  \"email\": \"test@test.edu.coo\",\n" +
                "  \"name\": \"Usuario de Prueba\",\n" +
                "  \"password\": \"mala\"\n" +
                "  }";
        user = new ObjectMapper().readValue( loginUser, LogInRequest.class);
        assertEquals(FAILED_BAD_REQUEST, loginController.loginUser(user).getStatusCode());

        //Succes login
        loginUser = "{\n" +
                "  \"email\": \"test@test.edu.coo\",\n" +
                "  \"name\": \"Usuario de Prueba\",\n" +
                "  \"password\": \"MICONTRASEÑA1\"\n" +
                "  }";
        user = new ObjectMapper().readValue( loginUser, LogInRequest.class);
        assertEquals(SUCCESS_OK, loginController.loginUser(user).getStatusCode());
    }
/**/
/**/
    @Test
    @Order(3)
    void deactivateUser() throws IOException {
        LoginController loginController = new LoginController(userService);
        //Invalid email
        String updateUser = "{\n" +
                "  \"email\": \"test@test.edu.cooo\"\n" +
                "}";
        User user = new ObjectMapper().readValue(updateUser, User.class);
        assertEquals(FAILED_BAD_REQUEST, loginController.deactivateUser(user).getStatusCode());

        //Succesful deactivate user
        updateUser = "{\n" +
                "  \"email\": \"test@test.edu.coo\"\n" +
                "}";
        user = new ObjectMapper().readValue(updateUser, User.class);
        assertEquals(SUCCESS_ACCEPTED, loginController.deactivateUser(user).getStatusCode());
    }

/**/
    @Test
    @Order(4)
    void deleteUser() throws IOException {
        LoginController loginController = new LoginController(userService);
        //Invalid email
        String deleteUser = "{\n" +
                "  \"email\": \"test@test.edu.coooo\"\n" +
                "}";
        User user = new ObjectMapper().readValue(deleteUser, User.class);
        assertEquals(FAILED_BAD_REQUEST, loginController.deleteUser(user).getStatusCode());

        //Succesful delete
        deleteUser = "{\n" +
                "  \"email\": \"test@test.edu.coo\"\n" +
                "}";
        user = new ObjectMapper().readValue(deleteUser, User.class);
        assertEquals(SUCCESS_ACCEPTED, loginController.deleteUser(user).getStatusCode());
    }
/**/
}
