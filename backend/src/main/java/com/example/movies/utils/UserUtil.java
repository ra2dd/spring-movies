package com.example.movies.utils;

import com.example.movies.models.User;
import com.example.movies.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtil {

    private final UserRepository userRepository;

    public User getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("Cannot find user by id: " + userId)
        );
    }

    public User getCurrentUser() {
        Jwt jwt = (Jwt) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        String sub = jwt.getClaim("sub");
        return getUserById(sub);
    }
}
