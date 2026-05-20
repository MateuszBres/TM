import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { SnackbarService } from '../core/snackbar.service';

interface ApiError {
  status: number;
  message: string;
}

const ERROR_MESSAGES: Record<string, string> = {
  USER_NOT_FOUND: 'Nie znaleziono użytkownika',
  TASK_NOT_FOUND: 'Nie znaleziono zadania',
  INVALID_CREDENTIALS: 'Nieprawidłowe dane logowania',
  INVALID_PASSWORD: "Nieprawidłowe hasło",
  INVALID_CURRENT_PASSWORD: "Nieprawidłowe aktualne hasło",
  PASSWORD_NOT_SAME: "Hasła nie sa takie same",
  PASSWORD_SAME_AS_OLD: "Nowe hasło nie może być takie samo jak stare",
  BAD_REQUEST: 'Nieprawidłowe żądanie',
  USER_ALREADY_EXISTS: 'Użytkownik już istnieje',
  VALIDATION_ERROR: 'Wypełnij wszystkie pola poprawnie',
  INTERNAL_ERROR: 'Wystąpił błąd serwera. Spróbuj ponownie później',
  NETWORK_ERROR: 'Brak połączenia z serwerem. Sprawdź połączenie internetowe',
  CLIENT_ERROR: 'Wystąpił błąd. Sprawdź wprowadzone dane',
  SERVER_ERROR: 'Wystąpił błąd serwera. Spróbuj ponownie później',
  AUTHENTICATION_ERROR: 'Sesja wygasła. Zaloguj się ponownie',
};

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const snack = inject(SnackbarService);
  const PUBLIC_ENDPOINTS = ['/login', '/register', '/api/login', '/api/register'];

  return next(req).pipe(
    catchError((err: HttpErrorResponse) => {
      if (err.error && typeof err.error === 'string' && err.error.includes('<!DOCTYPE')) {
        snack.error('Błąd serwera: endpoint nie istnieje lub jest niedostępny');
        return throwError(() => err);
      }

      if (err.status === 401 && PUBLIC_ENDPOINTS.some(url => req.url.includes(url))) {
        const apiError = err.error && typeof err.error === 'object' && 'message' in err.error
          ? err.error as ApiError
          : null;
        const friendlyMessage = apiError
          ? ERROR_MESSAGES[apiError.message] || apiError.message || 'Wystąpił błąd'
          : 'Brak autoryzacji';
        snack.error(friendlyMessage);
        return throwError(() => err);
      }

      if (err.error && typeof err.error === 'object' && 'message' in err.error) {
        const apiError = err.error as ApiError;
        const friendlyMessage = ERROR_MESSAGES[apiError.message] || apiError.message || 'Wystąpił błąd';
        snack.error(friendlyMessage);
      } else if (err.status >= 400 && err.status < 500) {
        const message = ERROR_MESSAGES['CLIENT_ERROR'];
        snack.error(message);
      } else if (err.status >= 500) {
        const message = ERROR_MESSAGES['SERVER_ERROR'];
        snack.error(message);
      } else if (err.status === 0) {
        const message = ERROR_MESSAGES['NETWORK_ERROR'];
        snack.error(message);
      }

      return throwError(() => err);
    })
  );
};