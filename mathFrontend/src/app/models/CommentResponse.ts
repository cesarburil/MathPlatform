import { AnswerResponse } from "./AnswerResponse";

export interface CommentResponse {
  id: number,
  title: string,
  username: string,
  answers: AnswerResponse[]
}
