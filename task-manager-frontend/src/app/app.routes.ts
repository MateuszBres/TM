import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { authGuard } from './auth/auth.guard';
import { adminGuard } from './auth/admin.guard';
import { TaskListComponent } from './tasks/task-list/task-list.component';
import { TaskFormComponent } from './tasks/task-form/task-form.component';
import { RegisterComponent } from './register/register.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { AdminPanelComponent } from './admin/admin-panel/admin-panel.component';

export const routes: Routes = [
  { path: '', redirectTo: 'tasks', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'tasks', component: TaskListComponent, canActivate: [authGuard] },
  { path: 'addTask', component: TaskFormComponent, canActivate: [authGuard] },
  { path: 'password', component: ChangePasswordComponent, canActivate: [authGuard] },
  { path: 'admin', component: AdminPanelComponent, canActivate: [adminGuard] },
  { path: '**', redirectTo: 'login' }
];
