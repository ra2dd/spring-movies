package com.example.movies.services;

import com.example.movies.dtos.UploadVideoResponse;
import com.example.movies.dtos.VideoDto;
import com.example.movies.mappers.VideoMapper;
import com.example.movies.models.Video;
import com.example.movies.repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final S3Service s3Service;
    private final VideoRepository videoRepository;

    public UploadVideoResponse uploadVideo(MultipartFile multipartFile) {
        // upload file to storage and save video data to database

        String videoUrl = s3Service.uploadFile(multipartFile);
        var video = new Video();
        video.setVideoUrl(videoUrl);

        var savedVideo = videoRepository.save(video);
        return new UploadVideoResponse(savedVideo.getId(), savedVideo.getVideoUrl());
    }

    public UploadVideoResponse uploadThumbnail(MultipartFile file, String videoId) {
        Video editedVideo = getVideoById(videoId);
        String thumbnailUrl = s3Service.uploadFile(file);

        editedVideo.setThumbnailUrl(thumbnailUrl);
        videoRepository.save(editedVideo);

        return new UploadVideoResponse(editedVideo.getId(), editedVideo.getThumbnailUrl());
    }

    public VideoDto editVideo(VideoDto videoDto) {
        // Find the video by id
        Video editedVideo = getVideoById(videoDto.getId());

        System.out.println(editedVideo);

        // Map videoDto object to video
        editedVideo.setTitle(videoDto.getTitle());
        editedVideo.setDescription(videoDto.getDescription());
        editedVideo.setTags(videoDto.getTags());
        editedVideo.setVideoStatus(videoDto.getVideoStatus());
        editedVideo.setThumbnailUrl(videoDto.getThumbnailUrl());

        System.out.println(editedVideo);

        // Save video to the database
        videoRepository.save(editedVideo);
        return videoDto;
    }

    Video getVideoById(String videoId) {
        return videoRepository.findById(videoId).orElseThrow(
                () -> new IllegalArgumentException("Cannot find video by id: " + videoId));
    }

    public VideoDto getVideoDetails(String videoId) {
        Video savedVideo = getVideoById(videoId);
        return VideoMapper.mapToVideoDto(savedVideo);
    }
}
