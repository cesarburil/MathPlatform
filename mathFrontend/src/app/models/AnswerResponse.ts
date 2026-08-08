export interface AnswerResponse {
  id: number,
  title: string,
  username: string,
  answers: AnswerResponse[]
}
