import { Component, inject } from '@angular/core';
import { AuthService } from '../auth.service';
import { UserRegistrationResponse } from '../userDto';
import { UserService } from '../user.service';
import { Router } from '@angular/router';

enum RegistrationStatus {
  Checking = "Checking",
  Registered = "Registered",
  Logged = "Logged",
  Error = "Error",
}

@Component({
  selector: 'app-auth-callback',
  standalone: true,
  imports: [],
  templateUrl: './auth-callback.component.html',
  styleUrl: './auth-callback.component.css'
})
export class AuthCallbackComponent {
  registrationStatus = RegistrationStatus;
  isUserAuthenticated: RegistrationStatus = RegistrationStatus.Checking;
  timer: number = 3;
  private authService = inject(AuthService);
  private userService = inject(UserService);
  private router = inject(Router);

  async redirectTimer() {
    let waitTime = 3
    for (let i = waitTime; i >= 1; i--) {
      this.timer = i;
      await new Promise(resolve => setTimeout(resolve, 1000));
    }
  }

  ngOnInit(): void {

    this.authService.isAuthenticated$.subscribe(isAuthenticated => {
      if(isAuthenticated) {
        this.userService.registerUser()
        .subscribe(status => {
          switch (status) {
            case UserRegistrationResponse.CREATED:
              this.isUserAuthenticated = RegistrationStatus.Registered;
              this.redirectTimer()
              .then(() => 
                // TODO: redirect user to component where it can configure its profile
                this.router.navigateByUrl(localStorage.getItem('originalPath') ?? '/')
              );
              break;
            
            case UserRegistrationResponse.ALREADY_REGISTERED:
              this.isUserAuthenticated = RegistrationStatus.Logged;
              this.redirectTimer()
              .then(() => 
                this.router.navigateByUrl(localStorage.getItem('originalPath') ?? '/')
              );
              break;

            default:
              this.isUserAuthenticated = RegistrationStatus.Error;
              break;
          }
        });
      }
    });
  }
}
