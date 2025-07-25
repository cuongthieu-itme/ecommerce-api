package com.ecommerce.security;

import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public String generateToken(Authentication authentication) {
       CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetailsService.loadUserById(userDetails.getId());

       Map<String, Object> claims = new HashMap<>();

        // Extract first role name from user's roles
        String role = user.getRoles().stream()
            .map(roleObj -> roleObj.getName().name()) // Convert enum to String
            .findFirst()
            .orElse("ROLE_USER");

       claims.put("role", role);

       Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(Long.toString(user.getId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    
//    public String generateToken(Authentication authentication) {
//        Object principal = authentication.getPrincipal();
//        Long userId;
//
//        if (principal instanceof CustomUserDetails) {
//            userId = ((CustomUserDetails) principal).getId();
//        } else if (principal instanceof User) {
//            userId = ((User) principal).getId();
//        } else {
//            throw new RuntimeException("Unsupported authentication principal: " + principal.getClass());
//        }
//
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
//
//        return Jwts.builder()
//                .setSubject(Long.toString(userId))
//                .setIssuedAt(now)
//                .setExpiration(expiryDate)
//                .signWith(SignatureAlgorithm.HS512, jwtSecret)
//                .compact();
//    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return (String) claims.get("role");
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}

