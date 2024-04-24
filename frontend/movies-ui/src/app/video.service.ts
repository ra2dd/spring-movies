import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UploadVideoReponse } from './upload-video/uploadVideoResponse';

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
}
