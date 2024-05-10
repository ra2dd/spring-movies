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

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final VideoUtil videoUtil;

    private final UserService userService;

    private final CommentRepository commentRepository;
    private final VideoRepository videoRepository;

    private final CommentMapper commentMapper;

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

        return commentMapper.mapToCommentDto(comment);
    }

    public List<CommentDto> getComments(String videoId) {
        Video videoById = videoUtil.getVideoById(videoId);

        return videoById.getCommentList().stream().map(
                commentId -> commentMapper.mapToCommentDto(getCommentById(commentId))
        ).toList();
    }

    private Comment getCommentById(String commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("Cannot find comment by id:" + commentId)
        );
    }
}
