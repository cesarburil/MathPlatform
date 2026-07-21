package br.com.cesarburil.mathBackend.question.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionRequest {
    private String title;
    private Long categoryId;
    private String description;
    private String difficulty;
    private String video;
    private List<AlternativeRequest> alternatives;
}
