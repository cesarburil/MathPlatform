import { Component, HostListener, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { LoginService } from '../../services/login.service';
import { ACCESS_DURATION_LABEL, ACCESS_PRICE_LABEL, PROFESSOR_PHOTO_URL } from '../../catalog';

@Component({
  selector: 'app-home',
  imports: [RouterLink],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home {
  readonly price = ACCESS_PRICE_LABEL;
  readonly duration = ACCESS_DURATION_LABEL;
  readonly photo = PROFESSOR_PHOTO_URL;
  readonly planOpen = signal(false);

  constructor(
    readonly login: LoginService,
    private router: Router,
    route: ActivatedRoute,
  ) {
    route.fragment.subscribe((fragment) => {
      if (fragment === 'plano' && !this.login.loggedIn()) {
        this.planOpen.set(true);
      }
    });
  }

  openPlan(): void {
    if (this.login.hasPremium()) {
      this.router.navigate(['/categories']);
      return;
    }
    if (this.login.loggedIn()) {
      this.router.navigate(['/pay']);
      return;
    }
    this.planOpen.set(true);
  }

  closePlan(): void {
    this.planOpen.set(false);
  }

  enterDemo(): void {
    this.login.enterDemo();
  }

  @HostListener('document:keydown.escape')
  onEscape(): void {
    this.closePlan();
  }
}
