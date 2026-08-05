import { CategoryResponse } from "./CategoryResponse";

export interface LessonResponse {
  id: number,
  title: string,
  categoryId: number,
  categoryName: string,
  description: string,
  video: string,
}