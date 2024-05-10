package com.example.movies.utils;

import com.example.movies.models.Video;
import com.example.movies.repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoUtil {

    private final VideoRepository videoRepository;

    public Video getVideoById(String videoId) {
        return videoRepository.findById(videoId).orElseThrow(
                () -> new IllegalArgumentException("Cannot find video by id: " + videoId));
    }
}
