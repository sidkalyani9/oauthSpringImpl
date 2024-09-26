package com.example.oauthImpl.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private JwtDecoder jwtDecoder;

    @GetMapping("/protected")
    public ResponseEntity<String> testDemo(){
        System.out.println("Hello11111");
        return ResponseEntity.ok("Hello");
    }

    @PostMapping("/auth/google")
    public Map<String, Object> googleLogin(@RequestBody Map<String, String> request, HttpServletResponse response) {
        String token = request.get("token"); // Get the token from the request body
        try {
            // Decode and validate the ID token
            Jwt decodedToken = jwtDecoder.decode(token);

            // Access the user's information from the token
            String email = decodedToken.getClaimAsString("email");
            String name = decodedToken.getClaimAsString("name");

            // Authenticate the user (this is where you can create a session or issue a JWT)
            // For now, we'll just return success and user details
            return Map.of(
                    "success", true,
                    "email", email,
                    "name", name
            );
        } catch (JwtException e) {
            // Token is invalid or expired
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Map.of("success", false, "error", "Invalid token");
        }
    }
}

