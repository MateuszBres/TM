import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs';

interface LoginRequest{
  email:string;
  password:string;
}

interface RegisterRequest{
  email:string;
  password:string;
}

interface changePasswordReques{
  currentPassowrd:string;
  newPassword:string;
  confirmPassword:string;
}

interface LoginResponse{
  token:string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  private API = 'http://localhost:8080';

  login(request: LoginRequest){
    return this.http.post<LoginResponse>(
      `${this.API}/login`,
      request)
      .pipe(
        tap(response =>{
          localStorage.setItem('token', response.token);
        })

      );
  }

  register(req: RegisterRequest){
    return this.http.post(`${this.API}/register`, req);
  }

  changePassword(req: changePasswordReques){
    console.log(req);
    return this.http.post(`${this.API}/password`,req)
  }

  logout(){
    localStorage.removeItem('token');
  }

  isLoggedIn(): boolean{
    return !!localStorage.getItem('token')
  }

  getToken(){
    return localStorage.getItem('token');
  }

}
