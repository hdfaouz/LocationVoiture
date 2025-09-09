import { Component } from '@angular/core';
import {Router, RouterLink, RouterLinkActive} from "@angular/router";
import {AuthService} from "../services/auth.service";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    RouterLink,
    RouterLinkActive,
    NgIf
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent{
  constructor(public authService: AuthService, private router: Router) {}

  onLogout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
