package br.com.cesarburil.mathBackend.question.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlternativeRequest {
    private String title;
    private boolean correct;
}
