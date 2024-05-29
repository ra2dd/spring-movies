package com.example.movies.repositories;

import com.example.movies.models.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

public interface VideoRepository extends MongoRepository<Video, String> {
    List<Video> findByUserIdIn(Set<String> userIds);
}
