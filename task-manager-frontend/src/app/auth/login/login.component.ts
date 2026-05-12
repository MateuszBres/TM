import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router, RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { SnackbarService } from '../../core/snackbar.service';
import { MatCard } from '@angular/material/card';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatButton } from '@angular/material/button';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MatCard,
  MatFormField, MatInput, MatLabel, MatButton,RouterModule

  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  
  loginForm: FormGroup;
constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    private router: Router,
    private snack: SnackbarService
  ){
  this.loginForm = this.fb.nonNullable.group({
    email: ['',[ Validators.required]],
    password:['',[Validators.required]]
  })}

  

  login(){
    this.auth.login(this.loginForm.getRawValue()).subscribe({
      next:() => {
        this.snack.success('Zalogowano')
        this.router.navigateByUrl('/tasks');
      }
      ,
      error:() =>{
         this.snack.error("zle dane logowania ");
      }
    });
  }
}
