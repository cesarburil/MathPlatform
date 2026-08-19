import { Injectable, signal } from '@angular/core';

export type ToastKind = 'error' | 'ok';

export interface ToastAction {
  label: string;
  path: string;
}

export interface Toast {
  id: number;
  kind: ToastKind;
  text: string;
  action?: ToastAction;
}

@Injectable({
  providedIn: 'root',
})
export class ToastService {
  readonly items = signal<Toast[]>([]);
  private nextId = 0;

  error(text: string, action?: ToastAction): void {
    this.push('error', text, action);
  }

  ok(text: string): void {
    this.push('ok', text);
  }

  dismiss(id: number): void {
    this.items.update((list) => list.filter((toast) => toast.id !== id));
  }

  private push(kind: ToastKind, text: string, action?: ToastAction): void {
    const id = ++this.nextId;
    this.items.update((list) => [...list, { id, kind, text, action }]);
    window.setTimeout(() => this.dismiss(id), action ? 8000 : 4500);
  }
}
