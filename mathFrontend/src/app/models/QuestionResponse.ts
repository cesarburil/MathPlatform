import { AlternativeResponse } from "./AlternativeResponse";

export interface QuestionResponse {
  id: number,
  title: string,
  categoryName: string,
  difficulty: string,
  video: string,
  alternatives: AlternativeResponse[]
}