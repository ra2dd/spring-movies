package com.example.movies.services;

import com.example.movies.dtos.UserInfoDto;
import com.example.movies.models.User;
import com.example.movies.repositories.UserRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${auth0.userInfoEndpoint}")
    private String userInfoEndpoint;

    private final UserRepository userRepository;

    public void registerUser(String tokenValue) {
        // Make a call to the userInfo endpoint
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create((userInfoEndpoint)))
                .setHeader("Authorization", String.format("Bearer %s", tokenValue))
                .build();

        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        try {
            // Fetch user details
            HttpResponse<String> responseString = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String body = responseString.body();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            UserInfoDto userInfoDto = objectMapper.readValue(body, UserInfoDto.class);

            // Save user details to database
            // TODO: map givenName, familyName and photo
            User user = User.builder()
                    .id(userInfoDto.getSub())
                    .emailAddress(userInfoDto.getName())
                    .build();

            userRepository.save(user);

        } catch (Exception exception) {
            System.out.println(
                    "Error occurred while sending request with bearer token to Auth provider");
        }
    }

    public User getCurrentUser() {
        Jwt jwt = (Jwt) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        String sub = jwt.getClaim("sub");
        return userRepository.findById(sub).orElseThrow(() ->
                new IllegalArgumentException("Cannot find user with id - " + sub));
    }

    public void addLikedVideoToUser(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.addVideoToLikedVideos(videoId);
        userRepository.save(currentUser);
    }

    public void removeLikedVideoFromUser(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.removeVideoFromLikedVideos(videoId);
        userRepository.save(currentUser);
    }

    public boolean isVideoAlreadyLiked(String videoId) {
        return getCurrentUser().getLikedVideos().stream().anyMatch(
                likedVideo -> likedVideo.equals(videoId));
    }
}
