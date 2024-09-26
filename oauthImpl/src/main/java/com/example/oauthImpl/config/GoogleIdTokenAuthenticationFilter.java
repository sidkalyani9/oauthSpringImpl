package com.example.oauthImpl.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class GoogleIdTokenAuthenticationFilter extends OncePerRequestFilter {

    private JwtDecoder jwtDecoder;
    public GoogleIdTokenAuthenticationFilter(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)  throws ServletException, IOException
    {
        System.out.println("inside filter");
        String token = request.getHeader("Authorization"); // Expecting "Bearer <token>"

        if (token != null && token.startsWith("Bearer ")) {
            try {
                System.out.println("Token");

                // Strip the "Bearer " prefix
                String jwtToken = token.substring(7);
                // Decode and validate the token
                Jwt jwt = jwtDecoder.decode(jwtToken);
                System.out.println("JWT: " + jwt);
                // Optionally convert to Authentication object and set it in the SecurityContext
                JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
                var authentication = converter.convert(jwt);

                // Set authentication in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // Handle token validation exceptions (e.g., log it, send an error response)
                System.out.println(e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
