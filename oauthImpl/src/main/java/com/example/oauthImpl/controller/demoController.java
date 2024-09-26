package com.example.oauthImpl.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class demoController {

//    @GetMapping("/")
//    public ResponseEntity<String> fetchHome(Model model, @AuthenticationPrincipal OAuth2User oAuth2User){
//        System.out.println("hi 1");
//        model.addAttribute("userName", oAuth2User.getAttribute("name"));
//        System.out.println("hi 2");
//        return ResponseEntity.ok(model.getAttribute("userName").toString());
//    }

    @GetMapping("/home")
    public ResponseEntity<String> fetchHome(Model model, @AuthenticationPrincipal OAuth2User oAuth2User){
        System.out.println("hi 1");
        model.addAttribute("userName", oAuth2User.getAttribute("name"));
        System.out.println("hi 2");
        return ResponseEntity.ok(model.getAttribute("userName").toString());
    }


    @GetMapping("/public/home")
    public ResponseEntity<String> fetchPublicHome(){
        System.out.println("hi 1");
        System.out.println("hi 2");
        return ResponseEntity.ok("NA");
    }
}
