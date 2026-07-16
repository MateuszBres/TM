import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs';
import { userResponse } from '../modules/admin/admin.service';

interface LoginRequest {
  email: string;
  password: string;
}

interface RegisterRequest {
  email: string;
  password: string;
}

interface changePasswordReques {
  currentPassowrd: string;
  newPassword: string;
  confirmPassword: string;
}

interface LoginResponse {
  token: string;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private API = '/api';
  currentUser?: userResponse;

  constructor(private http: HttpClient) {}

  getMe() {
    return this.http.get<userResponse>(`${this.API}/me`).pipe(
      tap((user) => {
        this.currentUser = user;
      }),
    );
  }

  isAdmin(): boolean {
    return this.currentUser?.role === 'ADMIN';
  }

  login(request: LoginRequest) {
    return this.http.post<LoginResponse>(`${this.API}/login`, request).pipe(
      tap((response) => {
        localStorage.setItem('token', response.token);
      }),
    );
  }

  register(req: RegisterRequest) {
    return this.http.post(`${this.API}/register`, req);
  }

  changePassword(req: changePasswordReques) {
    return this.http.post(`${this.API}/password`, req);
  }

  logout() {
    localStorage.removeItem('token');
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  getToken() {
    return localStorage.getItem('token');
  }
}
