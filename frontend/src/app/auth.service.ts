import { Injectable, inject } from "@angular/core";
import { OidcSecurityService, LoginResponse } from "angular-auth-oidc-client";
import { BehaviorSubject, Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  public isAuthenticated$: Observable<boolean> = this.isAuthenticatedSubject.asObservable();

  private oidcSecurityService = inject(OidcSecurityService);

  constructor() {
    this.checkAuth();
  }

  /**
   * Subscribes to OidcSecurityService observable.
   * Whenever the authentication status changes
   * LoginResponse emits a new value and subscription handler 
   * updates isAuthenticatedSubject with new auth status.
   */
  private checkAuth() {
    this.oidcSecurityService.checkAuth().subscribe((loginResponse: LoginResponse) => {
      this.isAuthenticatedSubject.next(loginResponse.isAuthenticated);
    });
  }

  /**
   * OAuth2/OIDC tokens have an expiration time.
   * Invoke this method at regular intervals to resubscribe
   * to oidcSecurityService in turn refreshing the token.
   * Also might help with providing accurate authentication status 
   * when there are network instability problems.
   */
  public refreshAuthStatus() {
    this.checkAuth();
  }

  public get isAuthenticated(): boolean {
    return this.isAuthenticatedSubject.value;
  }

  public login() {
    localStorage.setItem('originalPath', window.location.pathname)
    this.oidcSecurityService.authorize();
  }

  public logout() {
    this.oidcSecurityService
    .logoff()
    .subscribe(result => {
      this.isAuthenticatedSubject.next(false); // update global state
    })
  }
}