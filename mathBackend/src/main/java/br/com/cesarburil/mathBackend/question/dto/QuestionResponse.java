package br.com.cesarburil.mathBackend.question.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionResponse {
    private Long id;
    private String title;
    private String categoryName;
    private String difficulty;
    private String video;
    private List<AlternativeResponse> alternatives;
}
