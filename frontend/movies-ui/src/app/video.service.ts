import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VideoService {

  constructor(private httpClient: HttpClient) { }

  postVideo(file: File): Observable<any> {
    // Make Http Post Request to upload a video

    const formData = new FormData();
    formData.append('file', file, file.name);

    return this.httpClient.post("http://localhost:8080/api/videos", formData);
  }
}
