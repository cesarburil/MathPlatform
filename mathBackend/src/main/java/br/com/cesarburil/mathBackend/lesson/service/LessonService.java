package br.com.cesarburil.mathBackend.lesson.service;

import br.com.cesarburil.mathBackend.infra.exception.ResourceNotFoundException;
import br.com.cesarburil.mathBackend.lesson.converter.LessonConverter;
import br.com.cesarburil.mathBackend.lesson.dto.LessonRequest;
import br.com.cesarburil.mathBackend.lesson.dto.LessonResponse;
import br.com.cesarburil.mathBackend.lesson.model.Lesson;
import br.com.cesarburil.mathBackend.lesson.repository.LessonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {


    private final LessonRepository lessonRepository;

    private final LessonConverter lessonConverter;

    public LessonService(LessonRepository lessonRepository, LessonConverter lessonConverter) {
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
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> ResourceNotFoundException.of("Lesson", lessonId));
        return lessonConverter.lessonToResponse(lesson);
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
        if (!lessonRepository.existsById(id)) {
            throw ResourceNotFoundException.of("Lesson", id);
        }
        lessonRepository.deleteById(id);
        return id.toString();
    }
}
