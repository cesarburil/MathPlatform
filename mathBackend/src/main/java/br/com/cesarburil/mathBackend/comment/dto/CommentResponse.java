package br.com.cesarburil.mathBackend.comment.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private String title;
    private String username;
    private List<AnswerResponse> answers;
}
