import { Component, OnInit, inject } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { OidcSecurityService } from 'angular-auth-oidc-client';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    MatIconModule,
    MatToolbarModule,
    MatButtonModule,
    MatMenuModule
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  isUserAuthenticated: boolean = false;
  private readonly oidcSecurityService = inject(OidcSecurityService);
  
  ngOnInit(): void {
    this.oidcSecurityService.isAuthenticated$.subscribe( 
      ({isAuthenticated}) => {
        this.isUserAuthenticated = isAuthenticated;
      }
    )
  }

  login() {
    this.oidcSecurityService.authorize();
  }

  logout() {
    this.oidcSecurityService
      .logoff()
      .subscribe((result) => console.log(result));
  }
}
