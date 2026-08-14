import { AlternativeRequest } from "./AlternativeRequest";

export interface QuestionRequest {
  title: string,
  categoryId: number,
  description: string,
  difficulty: string,
  video: string,
  alternatives: AlternativeRequest[]
}

