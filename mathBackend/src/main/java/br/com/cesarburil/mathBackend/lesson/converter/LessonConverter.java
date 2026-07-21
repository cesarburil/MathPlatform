package br.com.cesarburil.mathBackend.lesson.converter;

import br.com.cesarburil.mathBackend.category.model.Category;
import br.com.cesarburil.mathBackend.category.repository.CategoryRepository;
import br.com.cesarburil.mathBackend.lesson.dto.LessonRequest;
import br.com.cesarburil.mathBackend.lesson.dto.LessonResponse;
import br.com.cesarburil.mathBackend.lesson.model.Lesson;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LessonConverter {


    private final CategoryRepository categoryRepository;

    public LessonConverter(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<LessonResponse> lessonListToResponse(List<Lesson> lessons) {

        return lessons.stream().map(lesson ->
                LessonResponse
                        .builder()
                        .id(lesson.getId())
                        .title(lesson.getTitle())
                        .video(lesson.getVideo())
                        .categoryName(lesson.getCategory().getTitle())
                        .description(lesson.getDescription())
                        .build()
                ).toList();

    }

    public LessonResponse lessonToResponse(Lesson lesson) {
        return LessonResponse
                .builder()
                .id(lesson.getId())
                .categoryName(lesson.getCategory().getTitle())
                .description(lesson.getDescription())
                .video(lesson.getVideo())
                .title(lesson.getTitle())
                .build();
    }

    public Lesson requestToLesson(LessonRequest request, Long id) {
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow();

            return Lesson
                    .builder()
                    .id(id)
                    .video(request.getVideo())
                    .category(category)
                    .description(request.getDescription())
                    .title(request.getTitle())
                    .build();

    }

    public Lesson requestToLesson(LessonRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow();

        return Lesson
                .builder()
                .video(request.getVideo())
                .category(category)
                .description(request.getDescription())
                .title(request.getTitle())
                .build();

    }

}
