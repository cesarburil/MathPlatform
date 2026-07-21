package br.com.cesarburil.mathBackend.comment.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerResponse {
    private Long id;
    private String title;
    private Long commentId;
}
