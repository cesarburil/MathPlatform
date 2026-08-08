import { Component, input } from '@angular/core';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-navbar-item',
  imports: [RouterLink],
  templateUrl: './navbar-item.html',
  styleUrl: './navbar-item.scss',
})
export class NavbarItem {

  path = input.required<string>();
  name = input();

}
