import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { SnackbarService } from '../core/snackbar.service';
import { TranslateService } from '@ngx-translate/core';

interface ApiError {
  status: number;
  message: string;
}

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const snack = inject(SnackbarService);
  const translate = inject(TranslateService);

  return next(req).pipe(
    catchError((err: HttpErrorResponse) => {
      const apiError = err.error as ApiError;

      const message = apiError?.message
      ? translate.instant(apiError.message)
      : translate.instant('INTERNAL_ERROR');

      snack.error(message);
      return throwError(() => err);
    })
  );
};
