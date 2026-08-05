import { CategoryResponse } from "./CategoryResponse";

export interface LessonResponse {
  id: number,
  title: string,
  category: CategoryResponse,
  description: string,
  video: string,
}