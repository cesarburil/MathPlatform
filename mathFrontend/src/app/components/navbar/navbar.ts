import { Component } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { NavbarItem } from "./navbar-item/navbar-item";

@Component({
  selector: 'app-navbar',
  imports: [NavbarItem],
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss',
})
export class Navbar {
  constructor(private loginService: LoginService) { };

  logout(): void {
    this.loginService.logout();
  }
}
