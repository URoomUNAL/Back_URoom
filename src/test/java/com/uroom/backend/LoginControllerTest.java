package com.uroom.backend;

//import org.h2.util.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uroom.backend.Controllers.LoginController;
import com.uroom.backend.Models.User;
import com.uroom.backend.Services.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.DataInput;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LoginControllerTest {
    public final HttpStatus SUCCESS_OK = HttpStatus.OK;
    public final HttpStatus SUCCESS_CREATED = HttpStatus.CREATED;
    public final HttpStatus SUCCESS_ACCEPTED = HttpStatus.ACCEPTED;
    public final HttpStatus FAILED_BAD_REQUEST = HttpStatus.BAD_REQUEST;
    public final HttpStatus FAILED_INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;

    @Autowired
    private UserService userService;
    LoginController loginController = new LoginController(userService);
    @Test
    void createUser() throws JSONException, IOException {

        //Invalid password
        JSONObject newUser = new JSONObject();
        newUser.put("email", "sdelgadom@unal.edu.coo");
        newUser.put("name", "Usuario de Prueba");
        newUser.put("password", "mala");
        newUser.put("cellphone", "3002674935");
        newUser.put("age", 35);
        newUser.put("is_student", true);
        User user = new ObjectMapper().readValue((DataInput) newUser, User.class);
        assertEquals(FAILED_BAD_REQUEST, loginController.signUp(user).getStatusCode());


        //Succesful sign up
        newUser = new JSONObject();
        newUser.put("email", "sdelgadom@unal.edu.coo");
        newUser.put("name", "Usuario de Prueba");
        newUser.put("password", "MICONTRASEÑA1");
        newUser.put("cellphone", "3002674935");
        newUser.put("age", 35);
        newUser.put("is_student", true);
        user = new ObjectMapper().readValue((DataInput) newUser, User.class);
        assertEquals(SUCCESS_CREATED, loginController.signUp(user).getStatusCode());


        //POSSIBLE DUPLICATED USER
        newUser = new JSONObject();
        newUser.put("email", "sdelgadom@unal.edu.coo");
        newUser.put("name", "Usuario de Prueba");
        newUser.put("password", "mala");
        newUser.put("cellphone", "3002674935");
        newUser.put("age", 35);
        newUser.put("is_student", true);
        user = new ObjectMapper().readValue((DataInput) newUser, User.class);
        assertEquals(FAILED_INTERNAL_SERVER_ERROR, loginController.signUp(user).getStatusCode());

    }

    @Test
    void loginUser() throws JSONException, IOException {
        //Invalid email
        JSONObject loginUser = new JSONObject();
        loginUser.put("name", "Usuario de Prueba");
        loginUser.put("email", "sdelgadom@unal.educoo");
        loginUser.put("password", "MICONTRASEÑA1");
        User user = new ObjectMapper().readValue((DataInput) loginUser, User.class);
        assertEquals(FAILED_BAD_REQUEST, loginController.loginUser(user).getStatusCode());

        //Invalid password
        loginUser = new JSONObject();
        loginUser.put("name", "Usuario de Prueba");
        loginUser.put("email", "sdelgadom@unal.edu.coo");
        loginUser.put("password", "mala");
        user = new ObjectMapper().readValue((DataInput) loginUser, User.class);
        assertEquals(FAILED_BAD_REQUEST, loginController.loginUser(user).getStatusCode());

        //Succes login
        loginUser = new JSONObject();
        loginUser.put("name", "Usuario de Prueba");
        loginUser.put("email", "sdelgadom@unal.edu.coo");
        loginUser.put("password", "MICONTRASEÑA1");
        user = new ObjectMapper().readValue((DataInput) loginUser, User.class);
        assertEquals(SUCCESS_OK, loginController.loginUser(user).getStatusCode());
    }

    @Test
    void deactivateUser() throws JSONException, IOException {

        //Invalid email
        JSONObject updateUser = new JSONObject();
        updateUser.put("email", "sdelgadom@unal.edu.co");
        User user = new ObjectMapper().readValue((DataInput) updateUser, User.class);
        assertEquals(FAILED_BAD_REQUEST, loginController.deactivateUser(user).getStatusCode());

        //Succesful deactivate user
        updateUser = new JSONObject();
        updateUser.put("email", "sdelgadom@unal.edu.coo");
        user = new ObjectMapper().readValue((DataInput) updateUser, User.class);
        assertEquals(SUCCESS_ACCEPTED, loginController.deactivateUser(user).getStatusCode());
    }


    @Test
    void deleteUser() throws JSONException, IOException {

        //Invalid email
        JSONObject deleteUser = new JSONObject();
        deleteUser.put("email", "sdelgadom@unal.edu.co");
        User user = new ObjectMapper().readValue((DataInput) deleteUser, User.class);
        assertEquals(FAILED_BAD_REQUEST, loginController.deleteUser(user).getStatusCode());

        //Succesful delete
        deleteUser = new JSONObject();
        deleteUser.put("email", "sdelgadom@unal.edu.coo");
        user = new ObjectMapper().readValue((DataInput) deleteUser, User.class);
        assertEquals(FAILED_BAD_REQUEST, loginController.deleteUser(user).getStatusCode());
    }

}
