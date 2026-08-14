import { Component, signal } from '@angular/core';
import { CategoriesService } from '../../../services/categories.service';
import { CategoryResponse } from '../../../models/CategoryResponse';

@Component({
  selector: 'app-category-manager',
  imports: [],
  templateUrl: './category-manager.html',
  styleUrl: './category-manager.scss',
})
export class CategoryManager {
  constructor(private categoriesService: CategoriesService) {}

  showForm = signal<boolean>(false);

  categoryName = signal<string>('');

  categoryId = signal<number>(0);

  selectedCategory = signal<CategoryResponse | null>(null);

  categories = signal<CategoryResponse[]>([]);

  ngOnInit(): void {
    this.categoriesService.get().subscribe((result) => this.categories.set(result));
  }

  create() {
    this.categoriesService.create({ title: this.categoryName() }).subscribe((result) => {
      this.categories.update((categories) => [...categories, result]);
      this.categoryName.set('');
      this.showForm.set(false);
    });
  }
  delete() {
    this.categoriesService.delete(this.categoryId()).subscribe(() => {
      this.categories.update((categories) =>
        categories.filter((category) => category.id !== this.categoryId()),
      );
    });
  }
  update() {
    this.categoriesService
      .update({ title: this.categoryName() }, this.categoryId())
      .subscribe((result) => {
        this.categories.update((categories) =>
          categories.map((category) => (category.id === this.categoryId() ? result : category)),
        );
        this.categoryName.set('');
        this.showForm.set(false);
      });
  }
  openForm(category: CategoryResponse | null) {
    if (category) {
      this.selectedCategory.set(category);
    } else {
      this.selectedCategory.set(null);
    }
    this.showForm.set(true);
  }
  closeForm() {
    this.showForm.set(false);
    this.categoryName.set('');
    this.categoryId.set(0);
  }
}
