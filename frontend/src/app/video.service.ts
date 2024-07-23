import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UploadVideoReponse } from './upload-video/uploadVideoResponse';
import { VideoDto, VideoDtoMetadataForm } from './videoDto';

@Injectable({
  providedIn: 'root'
})
export class VideoService {

  constructor(private httpClient: HttpClient) { }

  postVideo(file: File): Observable<UploadVideoReponse> {
    // Make Http Post Request to upload a video

    const formData = new FormData();
    formData.append('file', file, file.name);

    return this.httpClient.post<UploadVideoReponse>("http://localhost:8080/api/videos", formData);
  }

  postThumbnail(file: File, videoId: string): Observable<UploadVideoReponse> {
    // Make Http Post Request to upload a video

    const formData = new FormData();
    formData.append('file', file, file.name);
    formData.append('videoId', videoId);

    return this.httpClient.post<UploadVideoReponse>("http://localhost:8080/api/videos/thumbnail", formData);
  }

  putEditVideoMetadata(videoId: string, 
                       videoMetadata: VideoDtoMetadataForm): Observable<VideoDto> {
    return this.httpClient.put<VideoDto>(`http://localhost:8080/api/videos/${videoId}`, videoMetadata);
  }

  getVideoData(videoId: string): Observable<VideoDto> {
    return this.httpClient.get<VideoDto>("http://localhost:8080/api/videos/" + videoId);
  }

  postViewed(videoId: string): Observable<number> {
    return this.httpClient.post<number>(`http://localhost:8080/api/videos/${videoId}/viewed`, null);
  }

  postLike(videoId: string): Observable<number> {
    return this.httpClient.post<number>(`http://localhost:8080/api/videos/${videoId}/like`, null)
  }

  getIsVideoLikedByUser(videoId: string): Observable<boolean> {
    return this.httpClient.get<boolean>(`http://localhost:8080/api/user/isVideoLiked?id=${videoId}`)
  }

  getAllVideos(): Observable<Array<VideoDto>> {
    return this.httpClient.get<Array<VideoDto>>(`http://localhost:8080/api/videos`);
  }

  getSubscribedVideos(): Observable<Array<VideoDto>> {
    return this.httpClient.get<Array<VideoDto>>(`http://localhost:8080/api/videos/subscribed`);
  }
}