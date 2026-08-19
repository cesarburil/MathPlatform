import { HttpHandlerFn, HttpRequest } from "@angular/common/http";
import { catchError, throwError } from "rxjs";
import { LoginService } from "./services/login.service";
import { inject } from "@angular/core";
import { Router } from "@angular/router";

export function authInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn) {
  const loginService = inject(LoginService);
  const router = inject(Router);
  const authToken = localStorage.getItem("_");
  const headers = authToken
    ? req.headers.set('Authorization', `Bearer ${authToken}`)
    : req.headers;
  const newReq = req.clone({ headers });

  return next(newReq).pipe(
    catchError((error) => {
      const isAuthAttempt = req.url.includes('/login') || req.url.includes('/register');
      if (error.status === 401 && !isAuthAttempt) {
        loginService.clearSession();
        router.navigate(["/home"]);
      }
      return throwError(() => error);
    })
  );
}
