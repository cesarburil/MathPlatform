package br.com.cesarburil.mathBackend.comment.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AnswerRequest {
    private Long commentId;
    private String title;
}
