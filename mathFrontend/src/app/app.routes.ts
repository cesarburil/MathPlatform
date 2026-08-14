import { ActivatedRoute, Routes } from '@angular/router';
import { Home } from './components/home/home';
import { Login } from './components/login/login';
import { authguardGuard } from './authguard-guard';
import { Register } from './components/register/register';
import { Payment } from './components/payment/payment';
import { Categories } from './components/categories/categories';
import { About } from './components/about/about';
import { Questions } from './components/questions/questions';
import { Lessons } from './components/lessons/lessons';
import { Lesson } from './components/lessons/lesson/lesson';
import { Comments } from './components/comments/comments';
import { Details } from './components/comments/details/details';
import { Admin } from './components/admin/admin';
import { LessonManager } from './components/lessons/lesson-manager/lesson-manager';
import { CategoryManager } from './components/categories/category-manager/category-manager';
import { QuestionManager } from './components/questions/question-manager/question-manager';
import { PaymentError } from './components/payment/payment-error/payment-error';

export const routes: Routes = [
  { path: "login", component: Login },
  { path: "register", component: Register },
  { path: "about", component: About },
  {
    path: "", canActivate: [authguardGuard], children: [
      { path: "", pathMatch: "full", redirectTo: "home" },
      { path: "home", component: Home },
      { path: "pay", component: Payment},
      { path: "pay/error", component: PaymentError},
      { path: "categories", component: Categories},
      { path: "lessons/:categoryId", component: Lessons},
      { path: "watch/:lessonId", component: Lesson},
      { path: "questions", component: Questions},
      { path: "comments", component: Comments},
      { path: "comments/:commentId", component: Details},
      { path: "admin", component: Admin },
      { path: "admin/categories", component: CategoryManager},
      { path: "admin/questions", component: QuestionManager},
      { path: "admin/lessons", component: LessonManager},
    ]
  }
];
