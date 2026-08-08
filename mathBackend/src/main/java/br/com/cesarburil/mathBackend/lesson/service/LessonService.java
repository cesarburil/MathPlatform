package br.com.cesarburil.mathBackend.lesson.service;

import br.com.cesarburil.mathBackend.category.model.Category;
import br.com.cesarburil.mathBackend.category.repository.CategoryRepository;
import br.com.cesarburil.mathBackend.comment.repository.CommentRepository;
import br.com.cesarburil.mathBackend.lesson.converter.LessonConverter;
import br.com.cesarburil.mathBackend.lesson.dto.LessonRequest;
import br.com.cesarburil.mathBackend.lesson.dto.LessonResponse;
import br.com.cesarburil.mathBackend.lesson.model.Lesson;
import br.com.cesarburil.mathBackend.lesson.repository.LessonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {


    private final LessonRepository lessonRepository;

    private final LessonConverter lessonConverter;

    public LessonService(LessonRepository lessonRepository, LessonConverter lessonConverter, CategoryRepository categoryRepository) {
        this.lessonRepository = lessonRepository;
        this.lessonConverter = lessonConverter;
    }

    public List<LessonResponse> getAllLessons(int pageNum, int quantity) {

        Page<Lesson> lessons = lessonRepository.findAll(PageRequest.of(pageNum, quantity));
        return lessonConverter.lessonListToResponse(lessons.stream().toList());

    }

    public List<LessonResponse> getLessonsByCategoryId(Long id) {

        List<Lesson> lessons = lessonRepository.findAllByCategoryId(id);
        return lessonConverter.lessonListToResponse(lessons);

    }


    public LessonResponse getLessonById(Long lessonId) {
        Optional<Lesson> lesson = lessonRepository.findById(lessonId);
        return lesson.map(lessonConverter::lessonToResponse).orElse(LessonResponse.builder().build());

    }

    public LessonResponse createLesson(LessonRequest request) {

        Lesson aNewLesson = lessonConverter.requestToLesson(request);
        Lesson saved = lessonRepository.save(aNewLesson);
        return lessonConverter.lessonToResponse(saved);

    }

    public LessonResponse updateLesson(LessonRequest request, Long id) {

        Lesson updated = lessonConverter.requestToLesson(request, id);
        Lesson saved = lessonRepository.save(updated);
        return lessonConverter.lessonToResponse(saved);

    }

    public String deleteLesson(Long id) {
        lessonRepository.deleteById(id);
        return id.toString();

    }
}
