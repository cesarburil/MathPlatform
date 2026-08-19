import { Component, signal } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-admin',
  imports: [RouterLink],
  templateUrl: './admin.html',
  styleUrl: './admin.scss',
})
export class Admin {
  constructor (private adminService: AdminService) {}

  message = signal("");

  get() {
    this.adminService.get().subscribe(result => this.message.set(result));
  }

}
