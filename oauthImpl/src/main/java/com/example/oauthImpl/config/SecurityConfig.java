package com.example.oauthImpl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.List;

@Configuration
public class SecurityConfig {



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("hi1");
        http
                .csrf(csrf -> csrf.disable());// Disable CSRF for simplicity in local development
        System.out.println("hi2");
        http
                .cors(cors-> cors.disable())
//                .cors(cors -> cors.configurationSource(request -> {
//                    var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
//                    corsConfiguration.setAllowedOrigins(List.of("http://localhost:5173")); // Allow frontend
//                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//                    corsConfiguration.setAllowCredentials(true); // Allow credentials
//                    return corsConfiguration;
//                }))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/**").permitAll() // Allow access to the Google login endpoint
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new GoogleIdTokenAuthenticationFilter(jwtDecoder()), org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
//                .oauth2Login(Customizer.withDefaults());// Enable OAuth2 login


        return http.build();
    }

    // Configure JWT decoder for Google-issued tokens
    @Bean
    public JwtDecoder jwtDecoder() {
        // Replace with your Google Client ID
        String googleIssuerUri = "https://accounts.google.com";
        return JwtDecoders.fromIssuerLocation(googleIssuerUri);
    }

}
