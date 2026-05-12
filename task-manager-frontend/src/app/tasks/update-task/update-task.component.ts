import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButton } from "@angular/material/button";
import { MatCard } from "@angular/material/card";
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepicker, MatDatepickerInput, MatDatepickerToggle } from "@angular/material/datepicker";
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatError, MatFormField, MatLabel, MatSuffix } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { MatOption, MatSelect } from "@angular/material/select";
import { SnackbarService } from '../../core/snackbar.service';
import { futureOrPresentValidator } from '../date-validators';
import { Task } from '../task';

@Component({
  selector: 'app-update-task',
  imports: [CommonModule, MatCard, ReactiveFormsModule,
    MatFormField, MatLabel, MatInput, MatButton, MatSelect, MatOption, MatError,
     MatDatepickerInput, MatDatepicker, MatDatepickerToggle, MatNativeDateModule,
    MatSuffix],
  templateUrl: './update-task.component.html',
  styleUrl: './update-task.component.css'
})
export class UpdateTaskComponent {

  updateForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) public task: Task,
    private dialoRef: MatDialogRef<UpdateTaskComponent>,
    private snack: SnackbarService
  ){
    this.updateForm = this.fb.nonNullable.group({
      title:['', [Validators.required, Validators.minLength(3)]],
      description:['', [Validators.maxLength(500)]],
      status:['', [Validators.required]],
      dueDate:['', [Validators.required, futureOrPresentValidator]]
    })
    this.updateForm.patchValue(task);
  }

  update(){
    
    if(this.updateForm.invalid){
      this.snack.info("Uzupełnij wszystkie pola")
      return;
    }
    

    this.dialoRef.close(this.updateForm.getRawValue());
  }



  cancel(){
    this.dialoRef.close;
  }
}
