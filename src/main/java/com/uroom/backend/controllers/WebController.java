package com.uroom.backend.controllers;

import com.uroom.backend.services.UserService;
import com.uroom.backend.auth.jwt.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final UserService userService;

    final AuthenticationManager authenticationManager;

    final JwtUtil jwtUtils;

    public WebController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }
    

}