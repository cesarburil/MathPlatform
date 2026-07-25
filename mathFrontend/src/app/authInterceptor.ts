import { HttpHandlerFn, HttpRequest } from "@angular/common/http";
import { catchError, throwError } from "rxjs";
import { LoginService } from "./services/login.service";
import { inject } from "@angular/core";

export function authInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn) {
  const loginService = inject(LoginService);
  const authToken = localStorage.getItem("_");
  const newReq = req.clone({
    headers: req.headers.append('Authorization', `Bearer ${authToken}`),
  });

  return next(newReq).pipe(
    catchError((error) => {
      loginService.logout();
      return throwError(() => error);
    }
    ))
}