package com.example.movies.mappers;

import com.example.movies.dtos.VideoDto;
import com.example.movies.models.Video;

public class VideoMapper {
    public static VideoDto mapToVideoDto(Video video) {

        return VideoDto.builder()
                .id(video.getId())
                .title(video.getTitle())
                .description(video.getDescription())
                .userId(video.getUserId())
                .likes(video.getLikes())
                .dislikes(video.getDislikes())
                .tags(video.getTags())
                .videoUrl(video.getVideoUrl())
                .videoStatus(video.getVideoStatus())
                .viewCount(video.getViewCount())
                .thumbnailUrl(video.getThumbnailUrl())
                .commentList(video.getCommentList())
                .build();
    }
}
