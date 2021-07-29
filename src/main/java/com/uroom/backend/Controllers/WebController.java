package com.uroom.backend.Controllers;

import com.uroom.backend.Models.EntitiyModels.User;
import com.uroom.backend.Models.RequestModels.LoginRequest;
import com.uroom.backend.Models.ResponseModels.JwtResponse;
import com.uroom.backend.Services.AzureStorageService;
import com.uroom.backend.Services.UserService;
import com.uroom.backend.auth.jwt.JwtUtil;
import com.uroom.backend.auth.services.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class WebController {

    private final AzureStorageService azureStorageService;

    public WebController(AzureStorageService azureStorageService) {
        this.azureStorageService = azureStorageService;
    }

    @GetMapping("/photo")
    public String uploadPhoto(@RequestParam MultipartFile photo, @RequestParam String name){
        String link = "";
        try {
            link = azureStorageService.writeBlobFile(photo,name);
        } catch (IOException e) {
            System.out.println("No se pudo guardar la foto en Azure");
            e.printStackTrace();
        }
        return link;
    }
    

}