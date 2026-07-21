package br.com.cesarburil.mathBackend.lesson.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonResponse {
    private Long id;
    private String title;
    private String categoryName;
    private String description;
    private String video;
}
