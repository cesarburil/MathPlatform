package br.com.cesarburil.mathBackend.lesson.controller;

import br.com.cesarburil.mathBackend.lesson.dto.LessonRequest;
import br.com.cesarburil.mathBackend.lesson.dto.LessonResponse;
import br.com.cesarburil.mathBackend.lesson.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
@Tag(name = "lessons", description = "Controller to see and edit lessons data")
public class LessonController {

    private LessonService service;

    public LessonController(LessonService service) {
        this.service = service;
    }

    @GetMapping("/")
    @Operation(summary = "List existing lessons", description = "List lessons with pagination. Optional request params available.")
    @ApiResponse(responseCode = "200", description = "Lessons listed successfully")
    @ApiResponse(responseCode = "404", description = "No lessons found")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<List<LessonResponse>> getAllLessons(@RequestParam(defaultValue = "5") @Max(15) int quantity,
                                                              @RequestParam(defaultValue = "0") int pageNum) {
        return new ResponseEntity<>(service.getAllLessons(pageNum, quantity), HttpStatus.OK);
    }

    @GetMapping("/c/{categoryId}")
    @Operation(summary = "List lessons by category", description = "List lessons for a given category id")
    @ApiResponse(responseCode = "200", description = "Lessons listed successfully")
    @ApiResponse(responseCode = "404", description = "No lessons found for category")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<List<LessonResponse>> getLessonsByCategoryId(@PathVariable Long categoryId,
                                                                       @RequestParam(defaultValue = "5") @Max(15) int quantity,
                                                                       @RequestParam(defaultValue = "0") int pageNum) {
        return new ResponseEntity<>(service.getLessonsByCategoryId(categoryId), HttpStatus.OK);
    }

    @GetMapping("/{lessonId}")
    @Operation(summary = "Get lesson by id", description = "Return a single lesson")
    @ApiResponse(responseCode = "200", description = "Lesson found")
    @ApiResponse(responseCode = "404", description = "Lesson not found")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<LessonResponse> getLessonById(@PathVariable Long lessonId) {
        return new ResponseEntity<>(service.getLessonById(lessonId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    @Operation(summary = "New lesson", description = "Create a new lesson")
    @ApiResponse(responseCode = "201", description = "Lesson created successfully")
    @ApiResponse(responseCode = "404", description = "Category not found")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<LessonResponse> createLesson(@RequestBody LessonRequest request) {
        return new ResponseEntity<>(service.createLesson(request), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    @Operation(summary = "Update lesson", description = "Update an existing lesson by id")
    @ApiResponse(responseCode = "200", description = "Lesson updated successfully")
    @ApiResponse(responseCode = "404", description = "Lesson or category not found")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<LessonResponse> updateLesson(@RequestBody LessonRequest request, @PathVariable Long id) {
        return new ResponseEntity<>(service.updateLesson(request, id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete lesson", description = "Delete an existing lesson by id")
    @ApiResponse(responseCode = "204", description = "Lesson deleted successfully")
    @ApiResponse(responseCode = "404", description = "Lesson not found")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<String> deleteLesson(@PathVariable Long id) {
        return new ResponseEntity<>(service.deleteLesson(id), HttpStatus.NO_CONTENT);
    }

}
