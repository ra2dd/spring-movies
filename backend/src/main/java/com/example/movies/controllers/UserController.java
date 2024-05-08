package com.example.movies.controllers;

import com.example.movies.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String register(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();

        userService.registerUser(jwt.getTokenValue());
        return "User registration successfull";
    }

    @PostMapping("subscribe/{username}")
    public void subscribeToUser(@PathVariable String username) {
        userService.subscribeUser(username);
    }

    @GetMapping("/isVideoLiked")
    public boolean isVideoLiked(@RequestParam(name = "id") String videoId) {
        return userService.doUserLikedVideo(videoId);
    }
}
