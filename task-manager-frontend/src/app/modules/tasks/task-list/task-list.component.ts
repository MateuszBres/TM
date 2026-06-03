import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { RouterLink} from "@angular/router";
import { SnackbarService } from '../../../core/snackbar.service';
import { MatTableModule } from '@angular/material/table';
import {  MatButtonModule } from '@angular/material/button';
import {  MatCardModule } from "@angular/material/card";
import { Page, Task } from '../task';
import { formatDateForApi } from '../date-utils';

import { UpdateTaskComponent } from '../update-task/update-task.component';
import { MatDialog } from '@angular/material/dialog';
import { successResponse } from '../../../core/successResponse';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatSort, MatSortHeader, MatSortModule } from '@angular/material/sort';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MatTooltipModule } from '@angular/material/tooltip';
import { AuthService } from '../../../core/auth.service';
import { TaskResponse, TaskService, TaskStatus } from '../task.service';


@Component({
  selector: 'app-task-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink,
    MatButtonModule, MatCardModule, MatTableModule,
    MatPaginatorModule, MatSortModule, MatSortHeader,
    MatTooltipModule],
  templateUrl: './task-list.component.html',
  styleUrl: './task-list.component.css'
})
export class TaskListComponent implements AfterViewInit {

  tasks: TaskResponse[] = [];
  selectedStatus: TaskStatus   | null = null;
  totalElements = 0;
  pageSize = 5;
  
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort; 

  displayedColumns: string[] = [
  'title',
  'description',
  'status',
  'dueDate',
  'actions',
  'edit'
];


  constructor(
    private taskService: TaskService,
    private snack: SnackbarService,
    private dialog: MatDialog,
    public auth: AuthService
  ){}

  ngAfterViewInit(): void {
    this.auth.getMe().subscribe({
      error: () => {}
    });
    this.loadTask();

    this.paginator.page.subscribe(()=>{
      this.loadTask();
    });

    this.sort.sortChange.subscribe(()=>{
      this.paginator.pageIndex =0;
      this.loadTask();
    })
  }

  loadTask(){

      const pageIndex = this.paginator?.pageIndex ?? 0;
      const pageSize = this.paginator?.pageSize ?? this.pageSize;
      const dueDate = this.sort?.active ?? 'dueDate';
      const direction = this.sort?.direction ?? 'desc';

    return this.taskService.getPageAndStatus(
      pageIndex,
      pageSize,
      dueDate,
      direction,
      this.selectedStatus
    ).subscribe({
      next: (page: Page<Task>) =>{
      this.tasks = page.content;
      this.totalElements = page.totalElements;
    }});
    
  }

  filterBystatus(status:TaskStatus){
    if(this.selectedStatus == status){
      this.selectedStatus = null;
    }else{
      this.selectedStatus = status;
    }
    this.paginator.pageIndex = 0;
    this.loadTask();
  }

  deleteTask(id: number){
    return this.taskService.delete(id).subscribe(() =>{
      this.tasks = this.tasks.filter(t => t.id !==id);
      this.snack.info("Usunięto zadanie");
      this.loadTask();
    })
  }

  edit(task: Task){

    
    this.dialog
  .open(UpdateTaskComponent, {
    data: task
  })
  .afterClosed()
  .subscribe(updated =>{
    if(!updated) return;

    const raw = updated;

  const payload = {
    ...raw,
      dueDate: formatDateForApi(raw.dueDate)
  };
    this.taskService.update(task.id, payload).subscribe({
      next: () => {
        this.snack.info("Zadanie zaktualizowane");
        this.loadTask();
      }
    });
  });
  
}

  onPageChange(event: PageEvent){
    this.pageSize = event.pageSize;
    this.paginator.pageIndex = event.pageIndex;
    this.loadTask();
  }

}
