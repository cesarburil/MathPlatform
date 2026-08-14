import { Component, signal } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { Navbar } from "../navbar/navbar";
import { NavbarItem } from "../navbar/navbar-item/navbar-item";

@Component({
  selector: 'app-admin',
  imports: [Navbar, NavbarItem],
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
