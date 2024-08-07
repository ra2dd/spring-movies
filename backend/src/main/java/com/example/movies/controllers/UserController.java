package com.example.movies.controllers;

import com.example.movies.enums.UserRegistrationResponse;
import com.example.movies.services.UserService;
import com.example.movies.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserUtil userUtil;

    @GetMapping("/register")
    public UserRegistrationResponse registerUser() {
        Map<String, String> registrationData = userUtil.checkIfUserInContextIsRegistered();

        if (Boolean.parseBoolean(registrationData.get("registered"))) {
            return UserRegistrationResponse.ALREADY_REGISTERED;
        } else if (userService.registerUser(registrationData.get("bearer"))) {
                return UserRegistrationResponse.CREATED;
        } else {
            return UserRegistrationResponse.ERROR;
        }
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
