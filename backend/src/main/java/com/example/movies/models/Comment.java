package com.example.movies.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.concurrent.atomic.AtomicInteger;

@Document(value = "Comment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    private String id;
    private String content;
    private String userId;
    private AtomicInteger likes = new AtomicInteger(0);
    private AtomicInteger dislikes = new AtomicInteger(0);
}
