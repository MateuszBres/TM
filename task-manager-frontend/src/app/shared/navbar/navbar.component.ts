import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../auth/auth.service';

import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    RouterLink,
    MatMenuModule,
    MatIconModule,
    MatButtonModule,
    CommonModule
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {

  isAdmin = false;

  constructor(
    public auth: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {

    this.auth.getMe().subscribe(user =>{
        this.isAdmin = user.role === 'ADMIN';
    })

  }

  logout() {

    this.auth.logout();

    this.router.navigate(['/login']);
  }
}