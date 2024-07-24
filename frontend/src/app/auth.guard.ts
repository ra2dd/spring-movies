import { inject, Injectable } from "@angular/core";
import { AuthService } from "./auth.service";
import { map, take } from "rxjs";

export const authGuard = () => {
  const authService = inject(AuthService);

  return authService.isAuthenticated$.pipe(
    take(1), // take the latest value
    map(isAuthenticated => {
      if (isAuthenticated) {
        return true;
      } else {
        authService.login()
        return false;
      }
    })
  );
}