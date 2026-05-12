import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { successResponse } from '../successResponse';
import { Page, Task } from './task';

export type TaskStatus = "TODO" | "IN_PROGRESS" | "DONE";

export interface TaskResponse{
  id: number;
  title: string;
  description?: string;
  status: TaskStatus;
  dueDate: string;
}

export interface TaskRequest{
  title: string;
  description?: string;
  status: TaskStatus;
  dueDate: string;
}



@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private API = 'http://localhost:8080/tasks';

  constructor(private http: HttpClient) { }

  getAll(){
    return this.http.get<TaskResponse[]>(this.API);
  }

  
  getPageAndStatus(page:number, size: number, sort: string, direction: string, status: TaskStatus | null){

    let params = `?page=${page}&size=${size}&sort=${sort}&direction=${direction}`;
    if(status){
      params += `&status=${status}`;
    }
   
    return this.http.get<Page<Task>>
    (`${this.API}${params}`);
  }

  create(req: TaskRequest){
    return this.http.post<successResponse>(this.API,req);
  }

  update(id: number,req: TaskRequest){
    return this.http.patch<successResponse>(`${this.API}/${id}`,req);
    
  }

  delete(id: number){
    return this.http.delete(`${this.API}/${id}`);
  }

}
