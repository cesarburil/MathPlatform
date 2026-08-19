import { Component } from '@angular/core';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { LoginService } from '../../services/login.service';
import { ThemeService } from '../../services/theme.service';
import { NavbarItem } from "./navbar-item/navbar-item";
import { filter } from 'rxjs';

@Component({
  selector: 'app-navbar',
  imports: [NavbarItem, RouterLink],
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss',
})
export class Navbar {
  constructor(
    readonly login: LoginService,
    private router: Router,
    readonly theme: ThemeService,
  ) {
    this.login.refreshFromStorage();
    this.router.events.pipe(filter((e): e is NavigationEnd => e instanceof NavigationEnd))
      .subscribe(() => this.login.refreshFromStorage());
  }

  logout(): void {
    this.login.logout();
  }
}
