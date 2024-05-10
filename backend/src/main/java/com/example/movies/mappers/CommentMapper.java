package com.example.movies.mappers;

import com.example.movies.dtos.CommentDto;
import com.example.movies.models.Comment;
import com.example.movies.repositories.UserRepository;
import com.example.movies.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final UserUtil userUtil;

    public Comment mapToComment(CommentDto commentDto) {
        return Comment.builder()
                .id(commentDto.getId())
                .content(commentDto.getContent())
                .userId(commentDto.getUserId())
                .build();
    }

    public CommentDto mapToCommentDto(Comment comment) {
        String username = userUtil.getUserById(comment.getUserId()).getUsername();

        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .userId(comment.getUserId())
                .username(username)
                .build();
    }
}
