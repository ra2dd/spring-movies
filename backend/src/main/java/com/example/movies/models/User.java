package com.example.movies.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Document(value = "User")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private Set<String> subscribedTo;
    private Set<String> subscribers;
    private List<String> videoHistory;
    private Set<String> likedVideos = ConcurrentHashMap.newKeySet();
//    private Set<String> dislikedVideos;

    public String getFullName() {
        return this.firstName + ' ' + this.lastName;
    }

    public void addVideoToLikedVideos(String videoId) {
        likedVideos.add(videoId);
    }

    public void removeVideoFromLikedVideos(String videoId) {
        likedVideos.remove(videoId);
    }
}
