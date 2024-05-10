package com.example.movies.utils;

import com.example.movies.models.User;
import com.example.movies.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
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
}
