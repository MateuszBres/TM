import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButton } from "@angular/material/button";
import { MatCard } from "@angular/material/card";
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormField, MatLabel, MatSuffix } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { MatSelectModule } from '@angular/material/select';
import { SnackbarService } from '../../core/snackbar.service';
import { successResponse } from '../../successResponse';
import { futureOrPresentValidator } from '../date-validators';
import { TaskService } from '../task.service';

@Component({
  selector: 'app-task-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MatCard, MatFormField,
    MatLabel, MatInput, MatSelectModule, MatButton, MatDatepickerModule,
    MatSuffix,MatNativeDateModule],
  templateUrl: './task-form.component.html',
  styleUrl: './task-form.component.css'
})
export class TaskFormComponent {

  
  addForm: FormGroup;

  constructor(
    private taskService: TaskService,
    private fb: FormBuilder,
    private snack: SnackbarService
  ){
    this.addForm = this.fb.nonNullable.group({
    title: ['', [Validators.required,Validators.minLength(3)]],
    description: ['', [Validators.maxLength(500)]],
    dueDate: [null, [Validators.required, futureOrPresentValidator]]
  })

  }

  addTask(){

    if(this.addForm.invalid) return;
  
    const raw = this.addForm.getRawValue();

  const payload = {
    ...raw,
    dueDate: raw.dueDate ? this.formatDate(raw.dueDate) : null
  };

     this.taskService.create(payload).subscribe({
      next: (res: successResponse) => {
      this.snack.success(res.message ?? "Dodano"); 
      this.addForm.reset();
    }, error:(err) =>{
      this.snack.error(err.error?.message ?? "Błąd podczas dodawania zadania");
    }
  });
  }

 private formatDate(date: Date): string {
    return `${date.getFullYear()}-${(date.getMonth()+1)
      .toString().padStart(2,'0')}-${date.getDate()
      .toString().padStart(2,'0')}`;
  }

}
