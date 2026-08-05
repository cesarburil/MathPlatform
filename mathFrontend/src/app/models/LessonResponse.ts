import { CategoryResponse } from "./CategoryResponse";

export interface LessonResponse {
  id: number,
  title: string,
  categoryName: string,
  description: string,
  video: string,
}