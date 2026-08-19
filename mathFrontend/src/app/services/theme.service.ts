import { computed, Injectable, signal } from '@angular/core';

export type ThemeName = 'light' | 'dark';

const STORAGE_KEY = 'mp-theme';

@Injectable({
  providedIn: 'root',
})
export class ThemeService {
  readonly theme = signal<ThemeName>(readStoredTheme());
  readonly isDark = computed(() => this.theme() === 'dark');

  constructor() {
    this.apply(this.theme());
  }

  toggle(): void {
    const next: ThemeName = this.theme() === 'dark' ? 'light' : 'dark';
    this.theme.set(next);
    localStorage.setItem(STORAGE_KEY, next);
    this.apply(next);
  }

  private apply(theme: ThemeName): void {
    document.documentElement.setAttribute('data-theme', theme);
  }
}

function readStoredTheme(): ThemeName {
  return localStorage.getItem(STORAGE_KEY) === 'dark' ? 'dark' : 'light';
}
