import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, map } from 'rxjs';
import { UserRegistrationResponse } from './userDto';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private httpClient = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/api/user`;

  registerUser(): Observable<UserRegistrationResponse> {
    return this.httpClient.get<UserRegistrationResponse>(`${this.apiUrl}/register`);
  }
}
