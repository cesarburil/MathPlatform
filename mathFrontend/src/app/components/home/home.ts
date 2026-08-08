import { Component } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-home',
  imports: [RouterLink],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home {

  constructor(private loginService: LoginService) { };

  logout(): void {
    this.loginService.logout();
  }

}
