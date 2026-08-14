import { AlternativeResponse } from "./AlternativeResponse";

export interface QuestionResponse {
  id: number,
  title: string,
  categoryId: number,
  categoryName: string,
  difficulty: string,
  video: string,
  alternatives: AlternativeResponse[]
}