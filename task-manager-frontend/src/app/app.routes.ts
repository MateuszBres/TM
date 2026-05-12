import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { AppComponent } from './app.component';
import { authGuard } from './auth/auth.guard';
import { TaskListComponent } from './tasks/task-list/task-list.component';
import { TaskFormComponent } from './tasks/task-form/task-form.component';
import { RegisterComponent } from './register/register.component';
import { ChangePasswordComponent } from './change-password/change-password.component';

export const routes: Routes = [
    {path: 'login', component:LoginComponent},
    {path: '', component: LoginComponent},
    {path: 'register', component: RegisterComponent},
    {path: '', component: AppComponent, canActivate: [authGuard]},
    {path: 'tasks', component: TaskListComponent},
    {path: 'addTask', component: TaskFormComponent },
    {path:'password', component: ChangePasswordComponent}
];
