package br.com.cesarburil.mathBackend.question.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class VerifiedQuestionRequest {
    private Long questionId;
    private Long alternativeId;
}
