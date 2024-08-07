package com.example.movies.utils;

import com.example.movies.models.User;
import com.example.movies.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserUtil {

    private final UserRepository userRepository;

    public User getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("Cannot find user by id: " + userId)
        );
    }

    private Jwt getJwtFromContext() {
        return (Jwt) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public User getCurrentUser() {
        Jwt jwt = getJwtFromContext();

        String sub = jwt.getClaim("sub");
        return getUserById(sub);
    }

    public Map<String, String> checkIfUserInContextIsRegistered() {
        Jwt jwt = getJwtFromContext();

        String sub = jwt.getClaim("sub");
        Map<String, String> registrationData = new HashMap<>();

        if (userRepository.existsById(sub)) {
            registrationData.put("registered", "true");
        } else {
            registrationData.put("registered", "false");
            registrationData.put("bearer", jwt.getTokenValue());
        }

        return registrationData;
    }
}
