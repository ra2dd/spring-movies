package com.example.movies.mappers;

import com.example.movies.dtos.VideoDto;
import com.example.movies.models.User;
import com.example.movies.models.Video;
import com.example.movies.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoMapper {

    private final UserUtil userUtil;

    public VideoDto mapToVideoDto(Video video) {

        String username = userUtil.getUserById(video.getUserId()).getUsername();

        return VideoDto.builder()
                .id(video.getId())
                .title(video.getTitle())
                .description(video.getDescription())
                .username(username)
                .likes(video.getLikes().get())
//                .dislikes(video.getDislikes().get())
                .tags(video.getTags())
                .videoUrl(video.getVideoUrl())
                .videoStatus(video.getVideoStatus())
                .viewCount(video.getViewCount().get())
                .thumbnailUrl(video.getThumbnailUrl())
                .commentList(video.getCommentList())
                .build();
    }
}
