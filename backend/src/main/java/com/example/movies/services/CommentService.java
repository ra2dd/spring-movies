package com.example.movies.services;

import com.example.movies.dtos.CommentDto;
import com.example.movies.mappers.CommentMapper;
import com.example.movies.models.Comment;
import com.example.movies.models.User;
import com.example.movies.models.Video;
import com.example.movies.repositories.CommentRepository;
import com.example.movies.repositories.VideoRepository;
import com.example.movies.utils.VideoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final VideoUtil videoUtil;

    private final UserService userService;

    private final CommentRepository commentRepository;
    private final VideoRepository videoRepository;

    public CommentDto addComment(String videoId, String commentContent) {
        User currentUser = userService.getCurrentUser();

        // TODO: make comment content validation
        Comment comment = new Comment();
        comment.setContent(commentContent);
        comment.setUserId(currentUser.getId());
        commentRepository.save(comment);

        Video videoById = videoUtil.getVideoById(videoId);
        videoById.addComment(comment.getId());
        videoRepository.save(videoById);

        return CommentMapper.mapToCommentDto(comment, currentUser.getUsername());
    }
}
