import { Component } from '@angular/core';
import { NavbarComponent } from "../navbar/navbar.component";
import { AuthService } from '../../core/auth.service';
import { Router, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-layout',
  imports: [NavbarComponent,RouterOutlet],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.css'
})
export class LayoutComponent {

  constructor(
    private router: Router,
    private auth: AuthService
  ) { }

  
  showNavbar(): boolean{
    const hideOnRoutes = ['/login', '/register'];

    return this.auth.isLoggedIn() && !hideOnRoutes.includes(this.router.url);
  }
}
