package com.example.movies.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private String id;
    private String content;
    private String userId;
    private Integer likes;
    private Integer dislikes;
    private String username;
}
