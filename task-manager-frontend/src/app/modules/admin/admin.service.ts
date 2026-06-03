import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

interface userResponse {
  id: number;
  email: string;
  role: string;
  createdAt: Date;
}

export type { userResponse };

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  private API = '/api';

  getAllUsers() {
    return this.http.get<userResponse[]>(`${this.API}/admin`);
  }

  getUserById(id: number){
    return this.http.get(`${this.API}/admin/${id}`);
  }

  updateUserRole(id: number, role: string){
    return this.http.patch(`${this.API}/admin/${id}`, { role });
  }

  deleteUser(id: number){
    return this.http.delete(`${this.API}/admin/${id}`);
  }
}
