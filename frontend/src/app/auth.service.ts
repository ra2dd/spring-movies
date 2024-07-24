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

  private checkAuth() {
    this.oidcSecurityService.checkAuth().subscribe((loginResponse: LoginResponse) => {
      this.isAuthenticatedSubject.next(loginResponse.isAuthenticated);
    });
  }

  public refreshAuthStatus() {
    this.checkAuth();
  }

  public get isAuthenticated(): boolean {
    return this.isAuthenticatedSubject.value;
  }

  public login() {
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