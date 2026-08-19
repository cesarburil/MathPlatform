import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-toast',
  imports: [RouterLink],
  templateUrl: './toast.html',
  styleUrl: './toast.scss',
})
export class ToastHost {
  readonly toasts = inject(ToastService);
}
