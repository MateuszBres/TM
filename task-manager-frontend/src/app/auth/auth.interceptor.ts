import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { AuthService } from './auth.service';
import { SnackbarService } from '../core/snackbar.service';
import { Router } from '@angular/router';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  const auth = inject(AuthService);
  const snack = inject(SnackbarService);
  const router = inject(Router);


  const  PUBLIC_ENDPOINTS = ['/login', '/register'];

  if(PUBLIC_ENDPOINTS.some(url => req.url.includes(url))){
    return next(req);
  }


  const token = localStorage.getItem('token');

  if(token){
    req = req.clone({
      setHeaders:{
        Authorization: `Bearer ${token}` 
      }
    });
  }

  return next(req).pipe(
    catchError((err: HttpErrorResponse) =>{
      if(err.status == 401){
        snack.error(err.error?.message || "Brak autoryzacji");
        auth.logout();
        router.navigateByUrl('/login');
      }
      
      return throwError(()=> err);
    })
  );
};
