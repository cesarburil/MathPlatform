import { Component, signal } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { Navbar } from "./components/navbar/navbar";
import { ToastHost } from "./components/toast/toast";
import { filter } from 'rxjs';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Navbar, ToastHost],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected readonly title = signal('mathFrontend');
  protected readonly isAuthPage = signal(false);
  protected readonly isWatchPage = signal(false);

  constructor(router: Router) {
    router.events.pipe(filter((e): e is NavigationEnd => e instanceof NavigationEnd))
      .subscribe((e) => {
        const url = e.urlAfterRedirects;
        this.isAuthPage.set(url.startsWith('/login') || url.startsWith('/register') || url.startsWith('/about'));
        this.isWatchPage.set(url.startsWith('/watch'));
      });
  }
}
