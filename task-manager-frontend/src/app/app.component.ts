import { Component, OnInit } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { NavbarComponent } from "./shared/navbar/navbar.component";
import { AuthService } from './auth/auth.service';
import { CommonModule } from '@angular/common';
import { TranslateService } from '@ngx-translate/core';


@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NavbarComponent, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'task-manager-frontend';

  constructor(
    private router: Router,
    public auth: AuthService
    , private translate: TranslateService
  ){}

  ngOnInit(): void {
  
    this.translate.use('pl').subscribe({

    });
  }


  showNavbar(): boolean{
    const hideOnRoutes = ['/login', '/register'];

    return this.auth.isLoggedIn() && !hideOnRoutes.includes(this.router.url);
  }

 
}
