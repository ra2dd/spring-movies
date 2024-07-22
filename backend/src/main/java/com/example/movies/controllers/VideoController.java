package com.example.movies.controllers;

import com.example.movies.dtos.CommentDto;
import com.example.movies.dtos.UploadVideoResponse;
import com.example.movies.dtos.VideoDto;
import com.example.movies.services.CommentService;
import com.example.movies.services.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VideoDto> getVideos() {
        return videoService.getVideos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UploadVideoResponse uploadVideo(@RequestParam("file") MultipartFile file) {
        return videoService.uploadVideo(file);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public VideoDto editVideoMetadata(@RequestBody VideoDto videoDto) {
        return videoService.editVideo(videoDto);
    }

    @PostMapping("/thumbnail")
    @ResponseStatus(HttpStatus.CREATED)
    public UploadVideoResponse uploadThumbnail(@RequestParam("file") MultipartFile file, @RequestParam("videoId") String videoId) {
        return videoService.uploadThumbnail(file, videoId);
    }

    @GetMapping("/{videoId}")
    @ResponseStatus(HttpStatus.OK)
    public VideoDto getVideoDetails(@PathVariable String videoId) {
        return videoService.getVideoDetails(videoId);
    }

    @PostMapping("/{videoId}/like")
    @ResponseStatus(HttpStatus.OK)
    public Integer likeVideo(@PathVariable String videoId) {
        return videoService.likeVideo(videoId);
    }

    @PostMapping("/{videoId}/viewed")
    @ResponseStatus(HttpStatus.OK)
    public Integer videoViewed(@PathVariable String videoId) {
        return videoService.makeVideoViewed(videoId);
    }

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("App is working");
    }

    @GetMapping("/subscribed")
    @ResponseStatus(HttpStatus.OK)
    public List<VideoDto> getVideosFromSubscribedUsers() {
        return videoService.getVideosFromSubscribedUsers();
    }
}
