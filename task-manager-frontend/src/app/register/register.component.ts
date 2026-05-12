import { Component } from '@angular/core';
import { SnackbarService } from '../core/snackbar.service';
import { AuthService } from '../auth/auth.service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatCard } from "@angular/material/card";
import { MatFormField, MatLabel, MatError } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatButton } from "@angular/material/button";


@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, CommonModule, MatCard, MatFormField, MatLabel, MatInput, MatButton, MatError],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
 
  registerForm: FormGroup;

  constructor(
    private snack: SnackbarService,
    private service: AuthService,
    private fb: FormBuilder,
    private router: Router
  ){
    this.registerForm = this.fb.nonNullable.group({
      email: ['', [Validators.required,Validators.email]],
      password:['', [Validators.required, Validators.minLength(8),
        Validators.pattern(/^(?=.*[A-Z])(?=.*\d(?=.*[!@#$%^&*?]).+$)/)
      ] ]
    })
  }

  register(){
    this.service.register(this.registerForm.getRawValue()).subscribe({next:() =>{
      this.snack.success("Dodano użytkownika"),
      this.router.navigateByUrl("/login")
    },error:err =>{
      this.snack.error(err.error?.messsage);
    }
  });
  }

}
