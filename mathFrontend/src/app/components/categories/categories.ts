import { Component, signal } from '@angular/core';
import { CategoriesService } from '../../services/categories.service';
import { CategoryResponse } from '../../models/CategoryResponse';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-categories',
  imports: [RouterLink],
  templateUrl: './categories.html',
  styleUrl: './categories.scss',
})
export class Categories {
  constructor(categoriesService: CategoriesService) {

    categoriesService.get().subscribe(result => {
      this.categories.set(result);
    })

  };

  categories = signal<CategoryResponse[] | null>(null);



}
