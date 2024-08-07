package com.example.movies.services;

import com.example.movies.dtos.UserInfoDto;
import com.example.movies.models.User;
import com.example.movies.models.Video;
import com.example.movies.repositories.UserRepository;
import com.example.movies.utils.UserUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${auth0.userInfoEndpoint}")
    private String userInfoEndpoint;

    private final UserUtil userUtil;
    private final UserRepository userRepository;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    @PostConstruct
    private void init() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public boolean registerUser(String bearerToken) {
        // Make a call to the userInfo endpoint
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create((userInfoEndpoint)))
                .setHeader("Authorization", String.format("Bearer %s", bearerToken))
                .build();

        try {
            // Fetch user details
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Failed to fetch user info, status code: "
                        + response.statusCode());
            }

            UserInfoDto userInfoDto = objectMapper.readValue(response.body(), UserInfoDto.class);

            // Save user details to database
            // TODO: map givenName, familyName and photo
            User user = User.builder()
                    .id(userInfoDto.getSub())
                    .username(userInfoDto.getNickname())
                    .emailAddress(userInfoDto.getName())
                    .build();

            userRepository.save(user);
            return true;

        } catch (Exception exception) {
            System.out.println(
                    "Error occurred while sending request with bearer token to Auth provider: "
                            + exception);
        }
        return false;
    }

    public void addLikedVideoToUser(String videoId) {
        User currentUser = userUtil.getCurrentUser();
        currentUser.addVideoToLikedVideos(videoId);
        userRepository.save(currentUser);
    }

    public void removeLikedVideoFromUser(String videoId) {
        User currentUser = userUtil.getCurrentUser();
        currentUser.removeVideoFromLikedVideos(videoId);
        userRepository.save(currentUser);
    }

    public boolean isVideoAlreadyLiked(String videoId) {
        return userUtil.getCurrentUser().getLikedVideos().stream().anyMatch(
                likedVideo -> likedVideo.equals(videoId));
    }

    public void addVideoToUserVideoHistory(String videoId) {
        User currentUser = userUtil.getCurrentUser();
        currentUser.addVideoToVideoHistory(videoId);
        userRepository.save(currentUser);
    }

    public boolean doUserAlreadySubscribed(User subscriber, User subbedTo) {
        return subscriber.getSubscribedTo().stream().anyMatch(
                subscribedTo -> subscribedTo.equals(subbedTo.getId())
        );
    }

    public void subscribeUser(String username) {
        User currentUser = userUtil.getCurrentUser();
        User beingSubbedToUser = userRepository.findByUsername(username);

        if (doUserAlreadySubscribed(currentUser, beingSubbedToUser)) {
            beingSubbedToUser.removeFromSubscribers(currentUser.getId());
            currentUser.removeFromSubscribedTo(beingSubbedToUser.getId());
        } else {
            beingSubbedToUser.addSubscribers(currentUser.getId());
            currentUser.addSubscribedTo(beingSubbedToUser.getId());
        }

        userRepository.save(currentUser);
        userRepository.save(beingSubbedToUser);
    }

    public boolean doUserLikedVideo(String videoId) {
        User currentUser = userUtil.getCurrentUser();
        return currentUser.getLikedVideos().stream().anyMatch(
                likedVideoId -> likedVideoId.equals(videoId)
        );
    }

    public boolean isUserTheOwnerOfVideo(Video video) {
        User currentUser = userUtil.getCurrentUser();
        return video.getUserId().equals(currentUser.getId());
    }
}
