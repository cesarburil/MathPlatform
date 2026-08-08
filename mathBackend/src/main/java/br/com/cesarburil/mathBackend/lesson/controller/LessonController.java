package br.com.cesarburil.mathBackend.lesson.controller;

import br.com.cesarburil.mathBackend.comment.service.CommentService;
import br.com.cesarburil.mathBackend.lesson.dto.LessonRequest;
import br.com.cesarburil.mathBackend.lesson.dto.LessonResponse;
import br.com.cesarburil.mathBackend.lesson.service.LessonService;
import jakarta.validation.constraints.Max;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    private LessonService service;

    public LessonController(LessonService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<LessonResponse>> getAllLessons(@RequestParam(defaultValue = "5") @Max(15) int quantity,
                                                              @RequestParam(defaultValue = "0") int pageNum) {
        return new ResponseEntity<>(service.getAllLessons(pageNum, quantity), HttpStatus.OK);
    }

    @GetMapping("/c/{categoryId}")
    public ResponseEntity<List<LessonResponse>> getLessonsByCategoryId(@PathVariable Long categoryId,
                                                                       @RequestParam(defaultValue = "5") @Max(15) int quantity,
                                                                       @RequestParam(defaultValue = "0") int pageNum) {
        return new ResponseEntity<>(service.getLessonsByCategoryId(categoryId), HttpStatus.OK);
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<LessonResponse> getLessonById(@PathVariable Long lessonId) {
        return new ResponseEntity<>(service.getLessonById(lessonId), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<LessonResponse> createLesson(@RequestBody LessonRequest request) {
        return new ResponseEntity<>(service.createLesson(request), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LessonResponse> updateLesson(@RequestBody LessonRequest request, @PathVariable Long id) {
        return new ResponseEntity<>(service.updateLesson(request, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLesson(@PathVariable Long id) {
        return new ResponseEntity<>(service.deleteLesson(id), HttpStatus.OK);
    }

}
