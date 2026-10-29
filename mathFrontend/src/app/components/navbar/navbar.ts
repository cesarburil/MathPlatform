import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { LoginService } from '../../services/login.service';
import { ThemeService } from '../../services/theme.service';
import { NavbarItem } from './navbar-item/navbar-item';

@Component({
  selector: 'app-navbar',
  imports: [NavbarItem, RouterLink],
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss',
})
export class Navbar {
  constructor(
    readonly login: LoginService,
    readonly theme: ThemeService,
  ) {}

  logout(): void {
    this.login.logout();
  }
}
