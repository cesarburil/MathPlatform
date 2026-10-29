import { Component, input } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

export type NavIcon = 'home' | 'about' | 'lessons' | 'questions' | 'forum' | 'pay';

@Component({
  selector: 'app-navbar-item',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './navbar-item.html',
  styleUrl: './navbar-item.scss',
})
export class NavbarItem {
  readonly name = input.required<string>();
  readonly path = input.required<string>();
  readonly icon = input<NavIcon>('home');
}
