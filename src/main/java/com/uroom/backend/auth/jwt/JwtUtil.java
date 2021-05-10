package com.uroom.backend.auth.jwt;

import com.uroom.backend.auth.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;
/*
 * Clase responsable de crear y validar tokens
 * */
@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    @Value("${uroom.app.jwtsecret}")
    private String jwtSecret;
    @Value("${uroom.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    //metodo para generar tokens
    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    //obtener usuario desde el token desencriptando con nuestro secret
    public String getUserNameFromJwtToken(String token) {
        String username = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        Claims xd = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        Jws<Claims> hola = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        String pp = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getId();
        Date pp2 = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration();
        return username;
    }

    //validar token con nuestro secret
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}