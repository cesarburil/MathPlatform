package br.com.cesarburil.mathBackend.lesson.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonRequest {
    private String title;
    private Long categoryId;
    private String description;
    private String video;
}
