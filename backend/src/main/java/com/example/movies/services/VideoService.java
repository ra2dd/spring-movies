package com.example.movies.services;

import com.example.movies.dtos.UploadVideoResponse;
import com.example.movies.dtos.VideoDto;
import com.example.movies.mappers.VideoMapper;
import com.example.movies.models.Video;
import com.example.movies.repositories.UserRepository;
import com.example.movies.repositories.VideoRepository;
import com.example.movies.utils.VideoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoUtil videoUtil;

    private final S3Service s3Service;
    private final UserService userService;

    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    public UploadVideoResponse uploadVideo(MultipartFile multipartFile) {
        // upload file to storage and save video data to database

        String videoUrl = s3Service.uploadFile(multipartFile);
        var video = new Video();
        video.setVideoUrl(videoUrl);

        var savedVideo = videoRepository.save(video);
        return new UploadVideoResponse(savedVideo.getId(), savedVideo.getVideoUrl());
    }

    public UploadVideoResponse uploadThumbnail(MultipartFile file, String videoId) {
        Video editedVideo = videoUtil.getVideoById(videoId);
        String thumbnailUrl = s3Service.uploadFile(file);

        editedVideo.setThumbnailUrl(thumbnailUrl);
        videoRepository.save(editedVideo);

        return new UploadVideoResponse(editedVideo.getId(), editedVideo.getThumbnailUrl());
    }

    public VideoDto editVideo(VideoDto videoDto) {
        // Find the video by id
        Video editedVideo = videoUtil.getVideoById(videoDto.getId());

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


    public VideoDto getVideoDetails(String videoId) {
        Video savedVideo = videoUtil.getVideoById(videoId);
        return VideoMapper.mapToVideoDto(savedVideo);
    }


    public Integer likeVideo(String videoId) {
        // Get video by id
        Video videoById = videoUtil.getVideoById(videoId);


        // if video is already liked, remove a like
        if (userService.isVideoAlreadyLiked(videoId)) {
           videoById.decrementLikes();
           userService.removeLikedVideoFromUser(videoId);
        } else {
            videoById.incrementLikes();
            userService.addLikedVideoToUser(videoId);
        }

        videoRepository.save(videoById);
        return VideoMapper.mapToVideoDto(videoById).getLikes();
    }


    /**
     * Increments video view count and adds video to user video history
     * @param videoId
     * @return Incremented Video viewCount
     */
    public Integer makeVideoViewed(String videoId) {

        Video videoById = videoUtil.getVideoById(videoId);
        videoById.incrementViewCount();
        videoRepository.save(videoById);

        userService.addVideoToUserVideoHistory(videoId);
        return VideoMapper.mapToVideoDto(videoById).getViewCount();
    }
}
