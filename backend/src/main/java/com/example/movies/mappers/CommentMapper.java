package com.example.movies.mappers;

import com.example.movies.dtos.CommentDto;
import com.example.movies.models.Comment;

public class CommentMapper {
    public static Comment mapToComment(CommentDto commentDto) {
        return Comment.builder()
                .id(commentDto.getId())
                .content(commentDto.getContent())
                .userId(commentDto.getUserId())
                .build();
    }

    public static CommentDto mapToCommentDto(Comment comment, String username) {
        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .userId(comment.getUserId())
                .username(username)
                .build();
    }
}
