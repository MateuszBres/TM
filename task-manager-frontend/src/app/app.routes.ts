import { Routes } from '@angular/router';

import { TaskListComponent } from './modules/tasks/task-list/task-list.component';
import { TaskFormComponent } from './modules/tasks/task-form/task-form.component';

import { ChangePasswordComponent } from './modules/auth/change-password/change-password.component';
import { AdminPanelComponent } from './modules/admin/admin-panel/admin-panel.component';
import { LoginComponent } from './modules/auth/login/login.component';
import { RegisterComponent } from './modules/auth/register/register.component';
import { authGuard } from './core/auth.guard';
import { adminGuard } from './core/admin.guard';
import { LayoutComponent } from './layout/layout/layout.component';

export const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      { path: '', redirectTo: 'tasks', pathMatch: 'full' },

      { path: 'tasks', component: TaskListComponent, canActivate: [authGuard] },
      {
        path: 'addTask',
        component: TaskFormComponent,
        canActivate: [authGuard],
      },
      {
        path: 'password',
        component: ChangePasswordComponent,
        canActivate: [authGuard],
      },
      {
        path: 'admin',
        component: AdminPanelComponent,
        canActivate: [adminGuard],
      },
    ],
  },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: '**', redirectTo: 'login' },
];
