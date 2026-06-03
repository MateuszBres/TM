import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButton } from "@angular/material/button";
import { MatCard } from "@angular/material/card";
import { MatError, MatFormField, MatLabel } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { AuthService } from '../../../core/auth.service';
import { SnackbarService } from '../../../core/snackbar.service';
import { NgIf } from '@angular/common';
import { passwordMatchValidator } from '../password-marches.validator';

@Component({
  selector: 'app-change-password',
  imports: [ReactiveFormsModule, MatCard, MatFormField, MatLabel, MatInput, MatButton, MatError, NgIf],
  templateUrl: './change-password.component.html',
  styleUrl: './change-password.component.css'
})
export class ChangePasswordComponent {

  passwordForm: FormGroup;

  constructor(
    private service: AuthService,
    private fb: FormBuilder,
    private snack: SnackbarService
){
  this.passwordForm = this.fb.nonNullable.group({
    currentPassword:['',[Validators.required]],
    newPassword:[
      '',[Validators.required, Validators.minLength(8),
      Validators.pattern(/^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*?]).+$/)
        ]
      ],
    confirmPassword:['',[Validators.required]]
  },{
    validators: passwordMatchValidator
  
  }
);
  }
  get currentPassword(){
    return this.passwordForm.get('currentPassword');
  }
  get newPassword(){
    return this.passwordForm.get('newPassword');
  }
  get confirmPassword(){
    return this.passwordForm.get('confirmPassword');
  }
  changePassword(){
    if(this.passwordForm.invalid) return
    this.service.changePassword(this.passwordForm.getRawValue()).subscribe({next:
      ()=>{
        this.snack.success("Zmieniono hasło");
      }
    })
  }

}
