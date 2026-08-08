package br.com.cesarburil.mathBackend.question.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlternativeResponse {
    private Long id;
    private String title;
}
