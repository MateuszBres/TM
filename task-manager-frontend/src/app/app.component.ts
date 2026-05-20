import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { NavbarComponent } from "./shared/navbar/navbar.component";
import { AuthService } from './auth/auth.service';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NavbarComponent, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'task-manager-frontend';

  constructor(
    private router: Router,
    public auth: AuthService
  ){}


  showNavbar(): boolean{
    const hideOnRoutes = ['/login', '/register'];

    return this.auth.isLoggedIn() && !hideOnRoutes.includes(this.router.url);
  }
}
