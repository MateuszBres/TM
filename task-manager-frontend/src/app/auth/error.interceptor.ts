import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { SnackbarService } from '../core/snackbar.service';
import { TranslateService } from '@ngx-translate/core';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';

interface ApiError {
  status: number;
  message: string;
}

const PUBLIC_ENDPOINTS = ['/login', '/register', '/api/login', '/api/register'];

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const snack = inject(SnackbarService);
  const translate = inject(TranslateService);
  const auth = inject(AuthService);
  const router = inject(Router);

  if (!PUBLIC_ENDPOINTS.some(url => req.url.includes(url))) {
    const token = auth.getToken();

    if (token) {
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }
  }

  return next(req).pipe(
    catchError((err: HttpErrorResponse) => {
      const apiError = err.error as ApiError;

      if (err.status === 401) {
        auth.logout();
        router.navigateByUrl('/login');
      }

      const message = apiError?.message
        ? translate.instant(apiError.message)
        : translate.instant('INTERNAL_ERROR');

      snack.error(message);
      console.log(message);
      return throwError(() => err);
    })
  );
};
