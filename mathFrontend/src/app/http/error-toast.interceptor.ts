import { HttpErrorResponse, HttpHandlerFn, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { ToastService } from '../services/toast.service';
import { friendlyHttpMessage } from './http-error-message';

export function errorToastInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn) {
  const toasts = inject(ToastService);
  const isAuthAttempt = req.url.includes('/login') || req.url.includes('/register');

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 404) {
        return throwError(() => error);
      }

      if (error.status === 401 && !isAuthAttempt) {
        toasts.error('Sua sessão expirou. Entre novamente.', {
          label: 'Entrar',
          path: '/login',
        });
        return throwError(() => error);
      }

      toasts.error(friendlyHttpMessage(error, req.url));
      return throwError(() => error);
    }),
  );
}
