package com.example.movies.controllers;

import com.example.movies.services.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadVideo(@RequestParam("file") MultipartFile file) {
        videoService.uploadVideo(file);
    }

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test");
    }

    @GetMapping("/goodbye")
    public ResponseEntity<String> sayGoodbye() {
        return ResponseEntity.ok("Goodbye from API.");
    }
}