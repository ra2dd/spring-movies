package com.example.movies.controllers;

import com.example.movies.dtos.CommentDto;
import com.example.movies.models.Comment;
import com.example.movies.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{videoId}/comment")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto postComment(@PathVariable String videoId, String commentContent) {
        return commentService.addComment(videoId, commentContent);
    }

    @GetMapping("/{videoId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getComments(@PathVariable String videoId) {
        return commentService.getComments(videoId);
    }

}
